package com.ShiXi.user.IdentityAuthentication.teacherIdentification.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.domin.dto.TeacherUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.service.TeacherIdentificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/identification/teacher")
@Api(tags = "教师认证资料接口")
public class TeacherIdentificationController {
    @Resource
    TeacherIdentificationService teacherIdentificationService;
    @PostMapping("/uploadIdentificationData/fileType")
    @ApiOperation("教师发起身份验证，上传图片（身份证，教师从业资格证）资料")
    public Result uploadIdentificationPictureData(@RequestParam String type, @RequestParam MultipartFile file){
        return teacherIdentificationService.uploadIdentificationPictureData(type, file);
    }
    @PostMapping("/uploadIdentificationData/textType")
    @ApiOperation("教师发起身份验证，上传非图片资料（名字，学校，专业）")
    public Result uploadIdentificationTextData(@RequestBody TeacherUploadIdentificationTextDataReqDTO reqDTO){
        return teacherIdentificationService.uploadIdentificationTextData(reqDTO);
    }
    @GetMapping("/getMyIdentificationData")
    @ApiOperation("查看自己作为教师身份验证资料")
    public Result getMyIdentificationData(){
        return teacherIdentificationService.getMyIdentificationData();
    }
}
