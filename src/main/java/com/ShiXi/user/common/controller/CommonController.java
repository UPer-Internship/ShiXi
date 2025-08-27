package com.ShiXi.user.common.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.common.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户域通用服务")
public class CommonController {
    @Resource
    private UserService userService;

    @GetMapping("/common/getRegion")
    public Result getRegion(){
        return userService.getRegion();
    }
}
