package com.ShiXi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.ShiXi.dto.Result;
import com.ShiXi.dto.UserDTO;
import com.ShiXi.entity.User;
import com.ShiXi.mapper.UserMapper;
import com.ShiXi.service.UserService;
import com.ShiXi.utils.RegexUtils;
import com.ShiXi.utils.UserHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.ShiXi.utils.RedisConstants.*;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //基本不用密码登录 主要使用验证码登录
    @Override
    public Result loginByAccount(String account, String password) {
        //根据账号去mysql查询用户
        User user = query().eq("account", account).one();
        //mysql找不到
        if(user == null){
            return Result.fail("密码或账号错误！");
        }
        //mysql找到了，对比密码
        String RealPassword = user.getPassword();
        //密码正确
        if(password.equals(RealPassword)){
            //生成token
            String token = UUID.randomUUID().toString(true);
            //将student对象转换成安全DTO对象
            UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
            //序列化
            String userDTOJson= JSONUtil.toJsonStr(userDTO);
            //把token作为key，把用户DTO数据作为value存入redis并且设置过期时间
            stringRedisTemplate.opsForValue().set(token,userDTOJson);
            stringRedisTemplate.expire(token,30L, TimeUnit.MINUTES);
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
        String userDTOJson= JSONUtil.toJsonStr(userDTO);
        // 7.3.存储
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForValue().set(tokenKey,userDTOJson);
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
        // 3.符合，生成验证码
        String code = RandomUtil.randomNumbers(6);

        // 4.保存验证码到 session
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);

        // 5.发送验证码
        log.debug("发送短信验证码成功，验证码：{}", code);
        // 返回ok
        return Result.ok();

    }
    private User createStudentWithPhone(String phone) {
        // 1.创建用户
        User user = new User();
        user.setPhone(phone);
        user.setNickName(RandomUtil.randomString(10));
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
}
