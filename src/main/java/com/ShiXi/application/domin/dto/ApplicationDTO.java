package com.ShiXi.application.domin.dto;

import lombok.Data;

@Data
public class ApplicationDTO {
    private Long studentId;
    private Long enterpriseId;
    private Long jobId;
    private String resumeUrl;
    private String message; // 附加信息（含可到岗时间）
}
