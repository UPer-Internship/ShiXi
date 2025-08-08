package com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.domin.dto.TeacherTeamUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.service.TeacherTeamIdentificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/identification/teacherTeam")
@Api(tags = "教师认证资料接口")
public class TeacherTeamIdentificationController {
    @Resource
    TeacherTeamIdentificationService teacherTeamIdentificationService;
    @PostMapping("/uploadIdentificationData/fileType")
    @ApiOperation("教师发起身份验证，上传图片（身份证，教师从业资格证）资料")
    public Result uploadIdentificationPictureData(@RequestParam String type, @RequestParam MultipartFile file){
        return teacherTeamIdentificationService.uploadIdentificationPictureData(type, file);
    }
    @PostMapping("/uploadIdentificationData/textType")
    @ApiOperation("教师发起身份验证，上传非图片资料（名字，学校，专业）")
    public Result uploadIdentificationTextData(@RequestBody TeacherTeamUploadIdentificationTextDataReqDTO reqDTO){
        return teacherTeamIdentificationService.uploadIdentificationTextData(reqDTO);
    }
    @GetMapping("/getMyIdentificationData")
    @ApiOperation("查看自己作为教师身份验证资料")
    public Result getMyIdentificationData(){
        return teacherTeamIdentificationService.getMyIdentificationData();
    }
}
