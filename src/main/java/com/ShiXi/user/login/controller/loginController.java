package com.ShiXi.user.login.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.common.service.UserService;
import com.ShiXi.user.login.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/login")
@Api(tags = "用户服务")
public class loginController {
    @Resource
    private LoginService loginService;
    /**
     * 发送验证码
     * @param phone 手机号
     * @return
     */
    @PostMapping("/sendCode")
    @ApiOperation("发送验证码")
    public Result sendCode(@RequestParam("phone")String phone) {
        return loginService.sendCode(phone);
    }

    /**
     * 手机验证码登录
     * @param phone 手机号
     * @param code 验证码
     * @return 登录结果与token
     */
    @PostMapping("/byPhone")
    @ApiOperation("手机验证码登录")
    public Result loginByPhone(@RequestParam("phone")String phone,@RequestParam("code")String code) {
        return loginService.loginByPhone(phone,code);
    }

    /**
     * 用户登录
     *
     * @param code 微信返回的code
     * @param phone 手机号（新用户必填）
     * @return 用户信息
     */
    @PostMapping("/byWechat")
    @ApiOperation("微信登录")
    public Result login(@RequestParam String code, @RequestParam(required = false) String phone) {
        return loginService.loginByWechat(code, phone);
    }
}
