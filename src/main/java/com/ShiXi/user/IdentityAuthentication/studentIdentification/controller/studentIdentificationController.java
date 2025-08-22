package com.ShiXi.user.IdentityAuthentication.studentIdentification.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.domin.dto.StudentUploadIdentificationTextDataReqDTO;
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
    public Result uploadIdentificationPictureData(@RequestParam MultipartFile file){
        return studentIdentificationService.uploadIdentificationPictureData(file);
    }

    @PostMapping("/uploadIdentificationData/textType")
    @ApiOperation("学生发起身份验证，上传非图片资料（名字，学校，专业）")
    public Result uploadIdentificationTextData(@RequestBody StudentUploadIdentificationTextDataReqDTO reqDTO){
        return studentIdentificationService.uploadIdentificationTextData(reqDTO);
    }

    @GetMapping("/getMyIdentificationData")
    @ApiOperation("查看自己的图片类身份验证资料")
    public Result getMyIdentificationData(){
        return studentIdentificationService.getMyIdentificationData();
    }
}
