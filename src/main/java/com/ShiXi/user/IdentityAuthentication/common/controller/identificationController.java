package com.ShiXi.user.IdentityAuthentication.common.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
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

    @GetMapping("/getIdentificationStatus")
    @ApiOperation("获取用户的四种身份信息是否通过认证 0:未通过 1：已通过")
    public Result getIdentificationStatus(){
        Result identification = identificationService.getIdentificationStatus();
        return Result.ok(identification);
    }
    @PostMapping("/toIdentification")
    @ApiOperation("发起身份验证")
    public Result toIdentification(@RequestParam String identification,@RequestParam String type,@RequestParam MultipartFile file){
        return identificationService.toIdentification(identification, type, file);
    }

    @GetMapping("/getMyIdentificationData")
    @ApiOperation("查看自己的身份验证资料")
    public Result getMyIdentificationData(@RequestParam String identification, @RequestParam String type){
        return identificationService.getMyIdentification(identification, type);
    }
    @GetMapping("/getIdentificationDataByUserId")
    @ApiOperation("通过用户id查看身份验证资料")
    public Result getIdentificationDataByUserId(@RequestParam Integer userId,@RequestParam String identification, @RequestParam String type){
        return identificationService.getIdentificationDataByUserId(userId,identification,type);
    }
}
