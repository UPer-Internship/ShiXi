package com.ShiXi.user.info.studentInfo.domin.vo;

import lombok.Data;

import java.util.List;

@Data
public class StudentInfoVO {
    private Long userId;

    private String schoolName;

    private String major;

    private String graduationDate;

    private String educationLevel;

    private List<String> advantages;

    private List<String> expectedPosition;

    private List<String> tags;
}
