package com.ShiXi.user.info.studentInfo.domin.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class StudentInfoVO {
    private Long userId;

    private String schoolName;

    private String major;

    private String graduationDate;

    private String educationLevel;

    private String advantages;

    private String expectedPosition;

    private String phone;

    private String nickName;

    private String icon = "";

    private String gender;

    private String birthDate;

    private String name;

    private String wechat;
}
