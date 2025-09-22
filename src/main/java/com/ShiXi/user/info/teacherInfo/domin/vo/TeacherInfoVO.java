package com.ShiXi.user.info.teacherInfo.domin.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TeacherInfoVO {
    private Long userId;

    // 学校名称
    private String university;

    // 学院名称
    private String college;

    // 入职时间
    private LocalDate joinDate;

    // 邮箱地址
    private String email;
}
