package com.ShiXi.controller;

import com.ShiXi.dto.Result;
import com.ShiXi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    //使用密码登录已经弃用
    @PostMapping("/login/byAccount")
    public Result loginByAccount(@RequestParam("account")String account, @RequestParam("password")String password) {
        return userService.loginByAccount(account,password);
    }

    @PostMapping("/login/sendCode")
    public Result sendCode(@RequestParam("phone")String phone) {
        return userService.sendCode(phone);
    }

    @PostMapping("/login/byPhone")
    public Result loginByPhone(@RequestParam("phone")String phone,@RequestParam("code")String code) {
        return userService.loginByPhone(phone,code);
    }

}
