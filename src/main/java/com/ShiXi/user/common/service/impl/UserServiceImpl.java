package com.ShiXi.user.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.ShiXi.common.config.smsClientConfig;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.domin.vo.MajorVO;
import com.ShiXi.common.domin.vo.RegionVO;
import com.ShiXi.common.entity.Major;
import com.ShiXi.common.entity.Region;
import com.ShiXi.common.service.MajorsService;
import com.ShiXi.common.service.RegionService;
import com.ShiXi.common.utils.RedissonLockUtil;
import com.ShiXi.user.common.domin.dto.UserDTO;
import com.ShiXi.user.IdentityAuthentication.common.domin.vo.IdentificationVO;
import com.ShiXi.user.IdentityAuthentication.common.entity.Identification;
import com.ShiXi.user.common.entity.User;
import com.ShiXi.common.mapper.UserMapper;
import com.ShiXi.properties.WeChatProperties;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.ShiXi.user.common.service.UserService;
import com.ShiXi.feishu.service.impl.FeishuService;
import com.ShiXi.common.utils.HttpClientUtil;
import com.ShiXi.common.utils.RegexUtils;
import com.ShiXi.common.utils.UserHolder;
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

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.ShiXi.common.utils.MessageConstants.LOGIN_CODE_TEMPLATE_CODE;
import static com.ShiXi.common.utils.MessageConstants.SIGN_NAME;
import static com.ShiXi.common.utils.RedisConstants.*;
import static com.ShiXi.user.IdentityAuthentication.common.utils.RedisConstants.*;

// 在导入部分添加
import com.ShiXi.common.domin.vo.IndustryVO;
import com.ShiXi.common.entity.Industry;
import com.ShiXi.common.service.IndustryService;

// 在导入部分添加
import com.ShiXi.common.entity.University;
import com.ShiXi.common.service.UniversityService;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private FeishuService feishuService;
    @Resource
    private IdentificationService identificationService;
    @Resource
    private RegionService regionService;
    @Resource
    private WeChatProperties weChatProperties;
    //微信端登录接口请求的url
    public static String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    @Resource
    private MajorsService majorsService;
    @Resource
    private IndustryService industryService;
    @Resource
    private UniversityService universityService;

    @Resource
    RedissonLockUtil redissonLockUtil;

    //基本不用密码登录 主要使用验证码登录
    @Override
    public Result loginByAccount(String account, String password) {
        //根据账号去mysql查询用户
        User user = query().eq("account", account).one();
        //mysql找不到
        if (user == null) {
            return Result.fail("密码或账号错误！");
        }
        //mysql找到了，对比密码
        String RealPassword = user.getPassword();
        //密码正确
        if (password.equals(RealPassword)) {
            //生成token
            String token = UUID.randomUUID().toString(true);
            //将student对象转换成安全DTO对象
            UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
            //序列化
            String userDTOJson = JSONUtil.toJsonStr(userDTO);
            //把token作为key，把用户DTO数据作为value存入redis并且设置过期时间
            stringRedisTemplate.opsForValue().set(token, userDTOJson);
            stringRedisTemplate.expire(token, 30L, TimeUnit.MINUTES);
            //返回token
            return Result.ok(token);
        }
        //密码错误
        return Result.fail("密码或账号错误！");

    }

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

        // 返回ok
        return Result.ok();

    }

    private User createStudentWithPhone(String phone) {
        // 1.创建用户
        User user = new User();
        user.setPhone(phone);
        user.setNickName(RandomUtil.randomString(10));
        user.setUuid(UUID.randomUUID().toString(true)); // 生成UUID
        // 2.保存用户
        save(user);
        return user;
    }

    @Override
    public Result changeUserInfo(UserDTO userDTO) {
        //获取当前登录用户
        UserDTO currentUser = UserHolder.getUser();
        //获取当前登录用户的id
        Long userId = currentUser.getId();
        //根据id查询用户
        User user = query().eq("id", userId).one();
        //修改用户信息
        user.setNickName(userDTO.getNickName());
        user.setIcon(userDTO.getIcon());
        UserHolder.removeUser();
        UserHolder.saveUser(userDTO);
        updateById(user);
        return Result.ok(user);
    }

    /**
     * @param code 微信端请求的code
     * @return
     */
    @Override
    public Result loginByWechat(String code) {
        //获取 openid
        String openid = getOpenid(code);
        User user = query().eq("openid", openid).one();
        // 创建用户
        if (user == null) {
            user = new User();
            user.setOpenid(openid);
            save(user);
        }
        //登录流程，和电话流程一样
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

    @Override
    public Result getUserInfoById(Long id) {
        // 根据id查询用户
        User user = query().eq("id", id).one();
        if (user == null) {
            return Result.fail("用户不存在");
        }
        return Result.ok(user);
    }


    @Override
    public Result getUserInfoByUuid(String uuid) {
        // 根据uuid查询用户
        User user = query().eq("uuid", uuid).one();
        if (user == null) {
            return Result.fail("用户不存在");
        }
        return Result.ok(user);
    }


    @Override
    public Result getRegion() {
        String key = "regionList:";
        String regionListJson = stringRedisTemplate.opsForValue().get(key);
        if (regionListJson != null) {
            return Result.ok(JSONUtil.toBean(regionListJson, RegionVO.class));
        }

        boolean isLock = redissonLockUtil.tryLock(REBUILD_REGION_BUFFER_LOCK, 3, 10, TimeUnit.SECONDS);
        if (!isLock) {
            return Result.fail("请稍后再试");
        }
        List<Region> regionList = regionService.lambdaQuery().list();
        RegionVO regionVO = new RegionVO();

        // 按省份分组
        Map<String, List<Region>> provincesMap = regionList.stream()
                .collect(Collectors.groupingBy(Region::getProvince));

        List<RegionVO.Provinces> provincesList = new ArrayList<>();

        // 遍历每个省份
        for (Map.Entry<String, List<Region>> provinceEntry : provincesMap.entrySet()) {
            RegionVO.Provinces provinces = new RegionVO.Provinces();
            provinces.setProvince(provinceEntry.getKey());

            // 按城市分组
            Map<String, List<Region>> citiesMap = provinceEntry.getValue().stream()
                    .collect(Collectors.groupingBy(Region::getCity));

            List<RegionVO.cities> citiesList = new ArrayList<>();

            // 遍历每个城市
            for (Map.Entry<String, List<Region>> cityEntry : citiesMap.entrySet()) {
                RegionVO.cities cities = new RegionVO.cities();
                cities.setCity(cityEntry.getKey());

                // 获取区域列表
                List<String> districtList = cityEntry.getValue().stream()
                        .map(Region::getDistrict)
                        .collect(Collectors.toList());

                cities.setDistrict(districtList);
                citiesList.add(cities);
            }

            provinces.setCities(citiesList);
            provincesList.add(provinces);
        }

        regionVO.setRegions(provincesList);
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(regionVO));
        redissonLockUtil.unlock(REBUILD_REGION_BUFFER_LOCK);
        return Result.ok(regionVO);

    }

    @Override
    public Result getMajorList() {
        String key = "majorList:";
        String majorListJson = stringRedisTemplate.opsForValue().get(key);
        if (majorListJson != null) {
            return Result.ok(JSONUtil.toBean(majorListJson, MajorVO.class));
        }
        boolean isLock = redissonLockUtil.tryLock(REBUILD_MAJOR_BUFFER_LOCK, 3, 10, TimeUnit.SECONDS);
        if (!isLock) {
            return Result.fail("请稍后再试");
        }
        List<Major> majorList = majorsService.lambdaQuery().list();
        MajorVO majorVO = new MajorVO();


        Map<String, List<Major>> majorsMap = majorList.stream()
                .collect(Collectors.groupingBy(Major::getSecondLevelCategoryLabel));

        List<MajorVO.SecondLevelCategoryLabel> SecondLevelCategoryLabelList = new ArrayList<>();


        for (Map.Entry<String, List<Major>> SecondLevelCategoryLabelEntry : majorsMap.entrySet()) {
            MajorVO.SecondLevelCategoryLabel secondLevelCategoryLabel = new MajorVO.SecondLevelCategoryLabel();
            secondLevelCategoryLabel.setSecondLevelCategoryLabel(SecondLevelCategoryLabelEntry.getKey());


            Map<String, List<Major>> FirstLevelCategoryLabelsMap = SecondLevelCategoryLabelEntry.getValue().stream()
                    .collect(Collectors.groupingBy(Major::getFirstLevelCategoryLabel));

            List<MajorVO.FirstLevelCategoryLabel> firstLevelCategoryLabelList = new ArrayList<>();


            for (Map.Entry<String, List<Major>> firstLevelCategoryLabelEntry : FirstLevelCategoryLabelsMap.entrySet()) {
                MajorVO.FirstLevelCategoryLabel firstLevelCategoryLabel = new MajorVO.FirstLevelCategoryLabel();
                firstLevelCategoryLabel.setFirstLevelCategoryLabel(firstLevelCategoryLabelEntry.getKey());


                // 获取区域列表
                List<String> majorsList = firstLevelCategoryLabelEntry.getValue().stream()
                        .map(Major::getMajor)
                        .collect(Collectors.toList());

                firstLevelCategoryLabel.setMajor(majorsList);
                firstLevelCategoryLabelList.add(firstLevelCategoryLabel);
            }

            secondLevelCategoryLabel.setFirstLevelCategoryLabel(firstLevelCategoryLabelList);
            SecondLevelCategoryLabelList.add(secondLevelCategoryLabel);
        }

        majorVO.setMajors(SecondLevelCategoryLabelList);
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(majorVO));
        redissonLockUtil.unlock(REBUILD_MAJOR_BUFFER_LOCK);
        return Result.ok(majorVO);
    }

    @Override
    public Result getIndustryList() {
        String key = "industryList:";
        String industryListJson = stringRedisTemplate.opsForValue().get(key);
        if (industryListJson != null) {
            //缓存命中
            return Result.ok(JSONUtil.toBean(industryListJson, IndustryVO.class));
        }
        boolean isLock = redissonLockUtil.tryLock(REBUILD_INDUSTRY_BUFFER_LOCK, 3, 10, TimeUnit.SECONDS);
        if (!isLock) {
            //拿锁失败
            return Result.fail("请稍后再试");
        }
        List<Industry> industryList = industryService.lambdaQuery().list();
        IndustryVO industryVO = new IndustryVO();

        //获取一级行业分类的map
        Map<String, List<Industry>> industriesMap = industryList.stream()
                .collect(Collectors.groupingBy(Industry::getFirstLevelCategoryLabel));

        List<IndustryVO.FirstLevelCategoryLabel> firstLevelCategoryLabelList = new ArrayList<>();

        for (Map.Entry<String, List<Industry>> firstLevelCategoryLabelEntry : industriesMap.entrySet()) {
            IndustryVO.FirstLevelCategoryLabel firstLevelCategoryLabel = new IndustryVO.FirstLevelCategoryLabel();
            firstLevelCategoryLabel.setFirstLevelCategoryLabel(firstLevelCategoryLabelEntry.getKey());

            // 查行业列表
            List<String> industryNamesList = firstLevelCategoryLabelEntry.getValue().stream()
                    .map(Industry::getIndustryName)
                    .collect(Collectors.toList());

            firstLevelCategoryLabel.setIndustry(industryNamesList);
            firstLevelCategoryLabelList.add(firstLevelCategoryLabel);
        }

        industryVO.setIndustries(firstLevelCategoryLabelList);
        //缓存结果
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(industryVO));//TODO 感觉这里要加一个过期时间稍微维护一下一致性
        //放锁
        redissonLockUtil.unlock(REBUILD_INDUSTRY_BUFFER_LOCK);
        return Result.ok(industryVO);
    }

    @Override
    public Result getUniversityList() {
        String key = "universityList:";
        //查缓存
        String universityListJson = stringRedisTemplate.opsForValue().get(key);
        if (universityListJson != null) {
            return Result.ok(JSONUtil.toList(universityListJson, University.class));
        }
        //拿锁
        boolean isLock = redissonLockUtil.tryLock(REBUILD_UNIVERSITY_BUFFER_LOCK, 3, 10, TimeUnit.SECONDS);
        if (!isLock) {
            //获取锁失败
            return Result.fail("请稍后再试");
        }
        List<University> universityList = universityService.lambdaQuery().list();

        //缓存结果
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(universityList));
        //释放锁
        redissonLockUtil.unlock(REBUILD_UNIVERSITY_BUFFER_LOCK);
        return Result.ok(universityList);
    }


    @Override
    public User getMyUserInfo() {
        UserDTO userDTO = UserHolder.getUser();
        Long userId = userDTO.getId();
        User user = query().eq("id", userId).one();
        return user;
    }


}
