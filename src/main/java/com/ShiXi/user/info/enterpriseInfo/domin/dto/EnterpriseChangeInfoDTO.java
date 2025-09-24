package com.ShiXi.user.info.enterpriseInfo.domin.dto;

import lombok.Data;

@Data
public class EnterpriseChangeInfoDTO {
    private String enterpriseName; // 企业名称

    private String yourPosition; // 你的职位

    private String enterpriseScale; // 企业规模

    private String enterpriseIndustry; // 企业所属行业

    private String enterpriseAddress; // 企业地址

    private String email; // 申请人邮箱
}
