package com.ShiXi.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ApplicationDTO {
    private Long studentId;
    private Long enterpriseId;
    private Long jobId;
    private String resumeUrl;
    private String message; // 附加信息（含可到岗时间）
}
