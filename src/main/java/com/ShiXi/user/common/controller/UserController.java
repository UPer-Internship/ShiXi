package com.ShiXi.user.common.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.common.domin.dto.UserDTO;
import com.ShiXi.user.common.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户服务")
public class UserController {
    @Resource
    private UserService userService;


    @PostMapping("/changeInfo")
    public Result changeUserInfo(@RequestBody UserDTO userDTO){
        return userService.changeUserInfo(userDTO);
    }

    @GetMapping("/getUserInfoById")
    public Result getUserInfoById(@RequestParam Long id){
        return userService.getUserInfoById(id);
    }


}
