package com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.domin.dto.EnterpriseUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.service.EnterpriseIdentificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/identification/enterprise")
@Api(tags = "企业认证资料接口")
public class enterpriseIdentificationController {

    @Resource
    EnterpriseIdentificationService enterpriseIdentificationService;
    @PostMapping("/uploadIdentificationData/fileType")
    @ApiOperation("企业发起身份验证，上传图片（经营执照）资料")
    public Result uploadIdentificationPictureData(@RequestParam String type, @RequestParam MultipartFile file){
        return enterpriseIdentificationService.uploadIdentificationPictureData(type, file);
    }

    @PostMapping("/uploadIdentificationData/textType")
    @ApiOperation("企业发起身份验证，上传非图片资料（公司名称，公司类型，公司规模）")
    public Result uploadIdentificationTextData(@RequestBody EnterpriseUploadIdentificationTextDataReqDTO reqDTO){
        return enterpriseIdentificationService.uploadIdentificationTextData(reqDTO);
    }

    @GetMapping("/getMyIdentificationData")
    @ApiOperation("查看自己的企业身份验证资料")
    public Result getMyIdentificationData(){
        return enterpriseIdentificationService.getMyIdentificationData();
    }
}
