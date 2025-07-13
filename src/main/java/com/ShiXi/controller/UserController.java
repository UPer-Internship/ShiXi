package com.ShiXi.controller;

import com.ShiXi.dto.Result;
import com.ShiXi.dto.UserDTO;
import com.ShiXi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户服务")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 账号密码登录
     * @param account
     * @param password
     * @return
     */
    @PostMapping("/login/byAccount")
    @ApiOperation("账号密码登录,已弃用")
    public Result loginByAccount(@RequestParam("account")String account, @RequestParam("password")String password) {
        return userService.loginByAccount(account,password);
    }

    /**
     * 发送验证码
     * @param phone 手机号
     * @return
     */
    @PostMapping("/login/sendCode")
    @ApiOperation("发送验证码")
    public Result sendCode(@RequestParam("phone")String phone) {
        return userService.sendCode(phone);
    }

    /**
     * 手机验证码登录
     * @param phone 手机号
     * @param code 验证码
     * @return 登录结果与token
     */
    @PostMapping("/login/byPhone")
    @ApiOperation("手机验证码登录")
    public Result loginByPhone(@RequestParam("phone")String phone,@RequestParam("code")String code) {
        return userService.loginByPhone(phone,code);
    }

    /**
     * 用户登录
     *
     * @param code 微信返回的code
     * @return 用户信息
     */
    @PostMapping("/login/byWechat")
    @ApiOperation("微信登录")
    public Result login(@RequestParam  String code) {
         return userService.loginByWechat(code);
    }



    @PostMapping("/changeInfo")
    public Result changeUserInfo(@RequestBody UserDTO userDTO){
        return userService.changeUserInfo(userDTO);
    }

}
