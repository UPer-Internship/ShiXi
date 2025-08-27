package com.ShiXi.user.IdentityAuthentication.teacherIdentification.domin.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TeacherUploadIdentificationTextDataReqDTO {
    private String name; // 教师姓名
    private String gender; // 性别
    private String university; // 学校名称
    private String college; // 学院名称
    private LocalDate joinDate; // 入职时间
    private String email; // 邮箱地址
    private String workCertificateNumber; // 工作证明
}