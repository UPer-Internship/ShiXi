package com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.domin.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SchoolFriendUploadIdentificationTextDataReqDTO {
    private String name; // 姓名
    private String gender; // 性别
    private LocalDate birthDate; // 出生日期
    private String educationLevel; // 学历
    private String university; // 学校名称
    private String major; // 专业
    private LocalDate enrollmentDate; // 入学时间
    private LocalDate graduationDate; // 毕业时间
    private String graduationCertificateNumber;
}