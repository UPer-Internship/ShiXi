package com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.domin.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SchoolFriendUploadIdentificationTextDataReqDTO {
    private String name; // 姓名
    private String gender; // 性别
    private LocalDate birthDate; // 出生日期
    private String education; // 学历
    private String school; // 学校名称
    private String major; // 专业
    private LocalDate beginTime; // 入学时间
    private LocalDate graduationTime; // 毕业时间
}