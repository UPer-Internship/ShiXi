package com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.domin.dto.SchoolFriendUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.service.SchoolFriendIdentificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/identification/schoolFriend")
@Api(tags = "校友认证资料接口")
public class schoolFriendIdentificationController {

    @Resource
    SchoolFriendIdentificationService schoolFriendIdentificationService;
    
    @PostMapping("/uploadIdentificationData/fileType")
    @ApiOperation("校友发起身份验证，上传图片（毕业证书）资料")
    public Result uploadIdentificationPictureData(@RequestParam MultipartFile file){
        return schoolFriendIdentificationService.uploadIdentificationPictureData(file);
    }

    @PostMapping("/uploadIdentificationData/textType")
    @ApiOperation("校友发起身份验证，上传非图片资料（名字，学校，专业等）")
    public Result uploadIdentificationTextData(@RequestBody SchoolFriendUploadIdentificationTextDataReqDTO reqDTO){
        return schoolFriendIdentificationService.uploadIdentificationTextData(reqDTO);
    }

    @GetMapping("/getMyIdentificationData")
    @ApiOperation("查看自己的校友身份验证资料")
    public Result getMyIdentificationData(){
        return schoolFriendIdentificationService.getMyIdentificationData();
    }
}