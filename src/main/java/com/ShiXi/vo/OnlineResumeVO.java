package com.ShiXi.vo;

import com.ShiXi.entity.ResumeExperience;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 在线简历的vo类，用于组合在线简历的基础信息和实习经历等信息返回给前端
 */
@Data
@EqualsAndHashCode
public class OnlineResumeVO {
    // 基础简历信息
    private Long id;
    private Long userId;
    private String name;
    private String schoolName;
    private String major;
    private LocalDate graduationDate;
    private Integer age;
    private String phone;
    private String wechat;
    private String educationLevel;
    private String advantages;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 分类后的经历集合
    private List<ResumeExperience> internshipExperiences = new ArrayList<>();//实习
    private List<ResumeExperience> workExperiences = new ArrayList<>();//工作
    private List<ResumeExperience> projectExperiences = new ArrayList<>();//项目
    private List<ResumeExperience> portfolioExperiences = new ArrayList<>();//作品集
}
