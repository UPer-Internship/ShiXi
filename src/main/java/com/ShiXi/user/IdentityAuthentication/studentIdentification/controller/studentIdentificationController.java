package com.ShiXi.user.IdentityAuthentication.studentIdentification.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.service.StudentIdentificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/identification/student")
@Api(tags = "学生认证资料接口")
public class studentIdentificationController {

    @Resource
    StudentIdentificationService studentIdentificationService;
    @PostMapping("/uploadIdentificationData/fileType")
    @ApiOperation("学生发起身份验证，上传图片（身份证，学生证，毕业证）资料")
    public Result uploadIdentificationData(@RequestParam String type, @RequestParam MultipartFile file){
        return studentIdentificationService.toIdentification(type, file);
    }


    @GetMapping("/getMyIdentificationData/fileType")
    @ApiOperation("查看自己的图片类身份验证资料")
    public Result getMyIdentificationData(String type){
        return studentIdentificationService.getMyIdentification(type);
    }
}
