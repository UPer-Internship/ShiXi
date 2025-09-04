package com.ShiXi.user.IdentityAuthentication.studentTeamIdentification.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.studentTeamIdentification.domin.dto.StudentTeamUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.studentTeamIdentification.service.StudentTeamIdentificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/identification/studentTeam")
@Tag(name = "学生团队认证资料接口")
public class StudentTeamIdentificationController {
    @Resource
    StudentTeamIdentificationService studentTeamIdentificationService;
    
    @PostMapping("/uploadIdentificationData/fileType")
    @Operation(summary = "学生团队发起身份验证，上传图片（团队身份认证图片）资料")
    public Result uploadIdentificationPictureData(@RequestParam String type, @RequestParam MultipartFile file){
        return studentTeamIdentificationService.uploadIdentificationPictureData(type, file);
    }
    
    @PostMapping("/uploadIdentificationData/textType")
    @Operation(summary ="学生团队发起身份验证，上传非图片资料（团队名称，学校，学院，负责人信息等）")
    public Result uploadIdentificationTextData(@RequestBody StudentTeamUploadIdentificationTextDataReqDTO reqDTO){
        return studentTeamIdentificationService.uploadIdentificationTextData(reqDTO);
    }
    
    @GetMapping("/getMyIdentificationData")
    @Operation(summary ="查看自己作为学生团队身份验证资料")
    public Result getMyIdentificationData(){
        return studentTeamIdentificationService.getMyIdentificationData();
    }
}