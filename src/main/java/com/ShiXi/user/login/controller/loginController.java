package com.ShiXi.user.login.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.login.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/login")
@Tag(name = "用户登录服务")
public class loginController {
    @Resource
    private LoginService loginService;
    /**
     * 发送验证码
     * @param phone 手机号
     * @return
     */
    @PostMapping("/sendCode")
    @Operation(summary="发送验证码")
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
    @Operation(summary="手机验证码登录")
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
    @Operation(summary="微信登录")
    public Result login(@RequestParam String code, @RequestParam(required = false) String phone) {
        return loginService.loginByWechat(code, phone);
    }


    @PostMapping("/logout")
    @Operation(summary="登出")
    public Result logout() {
        return loginService.logout();
    }
    
    /**
     * 发送换绑手机号验证码
     * @param phone 手机号
     * @param type 类型：old-原手机号，new-新手机号
     * @return
     */
    @PostMapping("/sendChangePhoneCode")
    @Operation(summary="发送换绑手机号验证码")
    public Result sendChangePhoneCode(@RequestParam("phone")String phone, @RequestParam("type")String type) {
        return loginService.sendChangePhoneCode(phone, type);
    }

    /**
     * 验证原手机号
     * @param phone 原手机号
     * @param code 验证码
     * @return 验证结果
     */
    @PostMapping("/verifyOldPhone")
    @Operation(summary="验证原手机号")
    public Result verifyOldPhone(@RequestParam("phone")String phone, @RequestParam("code")String code) {
        return loginService.verifyOldPhone(phone, code);
    }

    /**
     * 换绑新手机号
     * @param newPhone 新手机号
     * @param code 新手机号验证码
     * @return 换绑结果
     */
    @PostMapping("/changePhone")
    @Operation(summary="换绑手机号")
    public Result changePhone(@RequestParam("newPhone")String newPhone, @RequestParam("code")String code) {
        return loginService.changePhone(newPhone, code);
    }
}
