package com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.domin.dto;

import lombok.Data;

/**
 * 企业身份认证文本数据上传请求DTO
 */
@Data
public class EnterpriseUploadIdentificationTextDataReqDTO {
    
    // 企业名称
    private String enterpriseName;
    
    // 申请人在企业中的职位
    private String yourPosition;
    
    // 企业规模
    private String enterpriseScale;
    
    // 企业所属行业
    private String enterpriseIndustry;
    
    // 企业地址
    private String enterpriseAddress;
    
    // 申请人真实姓名
    private String name;
    
    // 申请人性别
    private String gender;
    
    // 申请人邮箱
    private String email;
    
    // 营业执照号码或相关信息
    private String businessLicense;
}