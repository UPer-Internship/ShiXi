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

/**
 * 企业身份认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/user/identification/enterprise")
@Api(tags = "企业认证资料接口")
public class EnterpriseIdentificationController {

  @Resource
  EnterpriseIdentificationService enterpriseIdentificationService;

  /**
   * 企业发起身份验证，上传图片（营业执照等证件）资料
   * 
   * @param file 图片文件
   * @return 操作结果
   */
  @PostMapping("/uploadIdentificationData/fileType")
  @ApiOperation("企业发起身份验证，上传图片（营业执照等证件）资料")
  public Result uploadIdentificationPictureData(@RequestParam MultipartFile file) {
    return enterpriseIdentificationService.uploadIdentificationPictureData(file);
  }

  /**
   * 企业发起身份验证，上传非图片资料（企业名称、规模、行业等）
   * 
   * @param reqDTO 企业认证文本数据请求DTO
   * @return 操作结果
   */
  @PostMapping("/uploadIdentificationData/textType")
  @ApiOperation("企业发起身份验证，上传非图片资料（企业名称、规模、行业等）")
  public Result uploadIdentificationTextData(@RequestBody EnterpriseUploadIdentificationTextDataReqDTO reqDTO) {
    return enterpriseIdentificationService.uploadIdentificationTextData(reqDTO);
  }

  /**
   * 查看自己的企业身份验证资料
   * 
   * @return 企业认证数据
   */
  @GetMapping("/getMyIdentificationData")
  @ApiOperation("查看自己的企业身份验证资料")
  public Result getMyIdentificationData() {
    return enterpriseIdentificationService.getMyIdentificationData();
  }
}