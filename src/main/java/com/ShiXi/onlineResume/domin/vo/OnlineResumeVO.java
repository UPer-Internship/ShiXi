package com.ShiXi.onlineResume.domin.vo;

import com.ShiXi.onlineResume.entity.ResumeExperience;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 在线简历的vo类，用于组合在线简历的基础信息和实习经历等信息返回给前端
 */
@Data
@EqualsAndHashCode
public class OnlineResumeVO {
    // 基础简历信息
    private String name;//姓名
    private String gender;//性别
    private String birthDate;//出生年月
    private String icon;//头像
    private String schoolName;//学校名称
    private String major;//专业
    private LocalDate graduationDate;//毕业时间
    private String phone;//手机号
    private String wechat;//微信
    private String educationLevel;//学历
    private String advantages;//优势
    private String expectedPosition;//期望职位

    // 分类后的经历集合
    private List<ResumeExperience> internshipExperiences = new ArrayList<>();//实习
    private List<ResumeExperience> workExperiences = new ArrayList<>();//工作
    private List<ResumeExperience> projectExperiences = new ArrayList<>();//项目
    private List<ResumeExperience> portfolioExperiences = new ArrayList<>();//作品集
}
