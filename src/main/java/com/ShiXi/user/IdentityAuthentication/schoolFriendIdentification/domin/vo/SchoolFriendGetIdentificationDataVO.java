package com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.domin.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class SchoolFriendGetIdentificationDataVO {
    private Long userId; // 用户ID
    private Integer identification; // 认证状态
    private String graduationCertificateNumber; // 毕业证书
    private String name; // 姓名
    private String gender; // 性别
    private LocalDate birthDate; // 出生日期
    private String educationLevel; // 学历
    private String university; // 学校名称
    private String major; // 专业
    private LocalDate enrollmentDate; // 入学时间
    private LocalDate graduationDate; // 毕业时间
    private String pictureMaterial;
}