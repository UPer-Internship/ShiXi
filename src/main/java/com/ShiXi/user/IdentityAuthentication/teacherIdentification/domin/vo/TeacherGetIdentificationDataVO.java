package com.ShiXi.user.IdentityAuthentication.teacherIdentification.domin.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class TeacherGetIdentificationDataVO {
    private Long userId; // 用户ID
    private Integer identification; // 认证状态
    private String workCertificate; // 工作证明
    private String name; // 教师姓名
    private String nickname; // 教师昵称
    private String gender; // 性别
    private LocalDate birthDate; // 出生日期
    private String university; // 学校名称
    private String school; // 学院名称
    private LocalDate joinDate; // 入职时间
    private String email; // 邮箱地址
}