package com.ShiXi.user.IdentityAuthentication.teacherIdentification.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.domin.dto.TeacherUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.service.TeacherIdentificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/identification/teacher")
@Tag(name = "教师认证资料接口")
public class TeacherIdentificationController {

    @Resource
    TeacherIdentificationService teacherIdentificationService;

    @PostMapping("/uploadIdentificationData/fileType")
    @Operation(summary = "教师发起身份验证，上传图片（身份证，教师证，工作证明）资料")
    public Result uploadIdentificationPictureData(@RequestParam MultipartFile file){
        return teacherIdentificationService.uploadIdentificationPictureData(file);
    }

    @PostMapping("/uploadIdentificationData/textType")
    @Operation(summary = "教师发起身份验证，上传非图片资料（名字，学校，学院）")
    public Result uploadIdentificationTextData(@RequestBody TeacherUploadIdentificationTextDataReqDTO reqDTO){
        return teacherIdentificationService.uploadIdentificationTextData(reqDTO);
    }

    @GetMapping("/getMyIdentificationData")
    @Operation(summary = "查看自己的图片类身份验证资料")
    public Result getMyIdentificationData(){
        return teacherIdentificationService.getMyIdentificationData();
    }
}