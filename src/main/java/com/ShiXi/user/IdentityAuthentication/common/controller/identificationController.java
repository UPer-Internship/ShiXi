package com.ShiXi.user.IdentityAuthentication.common.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.ShiXi.user.common.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/identification")
public class identificationController {
    @Resource
    IdentificationService identificationService;

    @GetMapping("/getIdentification")
    @ApiOperation("获取用户的四种身份信息是否通过认证 0:未通过 1：已通过")
    public Result getIdentification(){
        Result identification = identificationService.getIdentification();
        return Result.ok(identification);
    }
}
