package com.ShiXi.user.login.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.ShiXi.common.config.smsClientConfig;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.entity.Options;
import com.ShiXi.common.mapper.UserMapper;
import com.ShiXi.common.service.OptionsService;
import com.ShiXi.common.utils.HttpClientUtil;
import com.ShiXi.common.utils.RegexUtils;
import com.ShiXi.feishu.service.impl.FeishuService;
import com.ShiXi.properties.WeChatProperties;
import com.ShiXi.user.IdentityAuthentication.common.entity.Identification;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.entity.EnterpriseIdentification;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.service.EnterpriseIdentificationService;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.entity.SchoolFriendIdentification;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.service.SchoolFriendIdentificationService;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.entity.StudentIdentification;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.service.StudentIdentificationService;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.entity.TeacherIdentification;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.service.TeacherIdentificationService;
import com.ShiXi.user.common.domin.dto.UserDTO;
import com.ShiXi.user.common.entity.User;
import com.ShiXi.user.login.service.LoginService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.ShiXi.common.utils.MessageConstants.LOGIN_CODE_TEMPLATE_CODE;
import static com.ShiXi.common.utils.MessageConstants.SIGN_NAME;
import static com.ShiXi.common.utils.RedisConstants.*;

@Slf4j
@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements LoginService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private FeishuService feishuService;
    @Resource
    private WeChatProperties weChatProperties;
    @Resource
    private IdentificationService identificationService;
    @Resource
    private OptionsService optionsService;
    // 微信端登录接口请求的url
    public static String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    @Resource
    private StudentIdentificationService studentIdentificationService;

    @Resource
    private TeacherIdentificationService teacherIdentificationService;
    @Resource
    private EnterpriseIdentificationService enterpriseIdentificationService;
    @Resource
    private SchoolFriendIdentificationService schoolFriendIdentificationService;

    @Override
    public Result loginByPhone(String phone, String code) {
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 2.如果不符合，返回错误信息
            return Result.fail("手机号格式错误！");
        }
        // 3.从redis获取验证码并校验
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        if (cacheCode == null || !cacheCode.equals(code)) {
            // 不一致，报错
            return Result.fail("验证码错误");
        }

        // 4.一致，根据手机号查询用户 select * from tb_user where phone = ?
        User user = query().eq("phone", phone).one();

        // 5.判断用户是否存在
        if (user == null) {
            // 6.不存在，创建新用户并保存
            user = createStudentWithPhone(phone);
        }

        // 7.保存用户信息到 redis中
        // 7.1.随机生成token，作为登录令牌
        String token = UUID.randomUUID().toString(true);
        // 7.2.将User对象转为HashMap存储
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        String userDTOJson = JSONUtil.toJsonStr(userDTO);
        // 7.3.存储
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForValue().set(tokenKey, userDTOJson);
        // 7.4.设置token有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

        // 8.返回token
        return Result.ok(token);
    }

    @Override
    public Result sendCode(String phone) {
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 2.如果不符合，返回错误信息
            return Result.fail("手机号格式错误！");
        }

        // 获取当前时间戳
        long currentTime = System.currentTimeMillis();

        // 从 Redis 获取该手机号上次发送验证码的时间
        String key = LOGIN_CODE_KEY + phone;
        String lastSendTimeStr = stringRedisTemplate.opsForValue().get(key + ":time"); // 使用新key存储时间戳

        if (lastSendTimeStr != null) {
            long lastSendTime = Long.parseLong(lastSendTimeStr);
            // 判断是否在60秒内已经发送过
            if (currentTime - lastSendTime < 60_000) { // 60秒限制
                return Result.fail("请勿频繁发送验证码");
            }
        }

        // 3.符合，生成验证码
        String code = RandomUtil.randomNumbers(6);

        // 4.保存验证码到 session
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);

        // 保存本次发送时间
        stringRedisTemplate.opsForValue().set(key + ":time", String.valueOf(currentTime), 60, TimeUnit.SECONDS); // 过期时间可略大于60s

        // 5.发送验证码
        log.debug("发送短信验证码成功，验证码：{}", code);
        feishuService.sendTextMessage(phone + ":" + code);
        // 构造请求对象
        Options sms = optionsService.lambdaQuery()
                .eq(Options::getOptionName, "sms").one();
        Integer status = sms.getOptionStatus();
        if (status == 1) {
            try {
                Client client = smsClientConfig.createClient();
                SendSmsRequest sendSmsRequest = new SendSmsRequest()
                        .setPhoneNumbers(phone)
                        .setSignName(SIGN_NAME)
                        .setTemplateCode(LOGIN_CODE_TEMPLATE_CODE)
                        // TemplateParam 为序列化后的 JSON 字符串。其中\"表示转义后的双引号。
                        .setTemplateParam("{\"code\":\"" + code + "\"}");

                // 发送 API 请求
                SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 返回ok
        return Result.ok();

    }

    @Transactional
    public User createStudentWithPhone(String phone) {
        // 1.创建用户
        User user = new User();
        user.setPhone(phone);
        user.setNickName(RandomUtil.randomString(10));
        save(user);
        // 初始化身份认证
        Identification identification = new Identification();
        identification.setUserId(user.getId());
        identificationService.save(identification);
        // 初始化学生认证资料
        StudentIdentification studentIdentification = new StudentIdentification();
        studentIdentification.setUserId(user.getId());
        studentIdentificationService.save(studentIdentification);
        // 初始化教师认证资料
        TeacherIdentification teacherIdentification = new TeacherIdentification();
        teacherIdentification.setUserId(user.getId());
        teacherIdentificationService.save(teacherIdentification);
        // 初始化企业认证资料
        EnterpriseIdentification enterpriseIdentification = new EnterpriseIdentification();
        enterpriseIdentification.setUserId(user.getId());
        enterpriseIdentificationService.save(enterpriseIdentification);
        // 初始化校友认证资料
        SchoolFriendIdentification schoolFriendIdentification = new SchoolFriendIdentification();
        schoolFriendIdentification.setUserId(user.getId());
        schoolFriendIdentificationService.save(schoolFriendIdentification);
        return user;
    }

    /**
     * @param code 微信端请求的code
     * @param phone 手机号（新用户必填）
     * @return
     */
    @Override
    public Result loginByWechat(String code, String phone) {
        // 获取 openid
        String openid = getOpenid(code);
        User user = query().eq("openid", openid).one();
        
        // 创建用户
        if (user == null) {
            // 新用户必须提供手机号
            if (phone == null || phone.isEmpty()) {
                return Result.fail("新用户首次登录必须提供手机号");
            }
            
            // 验证手机号格式
            if (RegexUtils.isPhoneInvalid(phone)) {
                return Result.fail("手机号格式错误！");
            }
            
            // 检查手机号是否已被使用
            User existingUser = query().eq("phone", phone).one();
            if (existingUser != null) {
                // 如果手机号已存在，则将openid绑定到已有账号
                existingUser.setOpenid(openid);
                updateById(existingUser);
                user = existingUser;
            } else {
                // 创建新用户，使用createStudentWithPhone方法并设置openid
                user = createStudentWithPhone(phone);
                user.setOpenid(openid);
                updateById(user);
            }
        }
        
        // 登录流程，和电话流程一样
        String token = UUID.randomUUID().toString(true);
        // 将User对象转为HashMap存储
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        String userDTOJson = JSONUtil.toJsonStr(userDTO);
        // 存储
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForValue().set(tokenKey, userDTOJson);
        // 设置token有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);
        // 返回token
        return Result.ok(token);
    }

    /**
     * 获取用户的openid
     *
     * @param code
     * @return
     */
    private String getOpenid(String code) {
        HashMap<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");

        // 调用微信接口服务，获取微信用户信息
        String result = HttpClientUtil.doGet(WX_LOGIN, map);

        JSONObject jsonObject = JSON.parseObject(result);
        String openid = jsonObject.getString("openid");
        if (openid == null) {
            throw new RuntimeException("获取微信openid失败");
        }
        return openid;
    }

}
