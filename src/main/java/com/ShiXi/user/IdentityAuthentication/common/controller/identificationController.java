package com.ShiXi.user.IdentityAuthentication.common.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.common.domin.dto.InitiateIdentificationDTO;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.ShiXi.user.common.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping("/toIdentification")
    @ApiOperation("发起身份验证")
    public Result toIdentification(@RequestParam String identification,@RequestParam String type,@RequestParam MultipartFile file){
        return identificationService.toIdentification(identification, type, file);
    }

    @GetMapping("/getMyIdentification")
    @ApiOperation("查看自己的身份验证资料")
    public Result getMyIdentification(@RequestParam String identification,@RequestParam String type){
        return identificationService.getMyIdentification(identification, type);
    }
//    @GetMapping("/getMyIdentification")
//    @ApiOperation("查看身份验证资料")
//    public Result getIdentification(@RequestParam String identification,@RequestParam String type){
//        return identificationService.getIdentification(identification, type);
//    }
}
