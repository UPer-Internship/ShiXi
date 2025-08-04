package com.ShiXi.onlineResume.domin.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UpdateResumeDTO {

    private String identification;

    private String advantages;

    private String expectedPosition;

    private String phone;// 手机号

    private String gender;

    private String birthDate;

    private String name;

    private String wechat;
    //oss附件链接
    private String resumeLink;
    //教育经历
    private List<educationExperience> educationExperiences;
    //工作实习经历列表
    private List<workAndInternshipExperience> workAndInternshipExperiences;
    //项目经历列表
    private List<projectExperience> projectExperiences;
    @Data
    public static class educationExperience implements Serializable {
        private String schoolName; // 学校名称
        private String major; // 专业
        private String startDate; // 开始时间
        private String endDate; // 结束时间
        private String educationLevel; //本科专科。。。。。
    }
    @Data
    public static class workAndInternshipExperience implements Serializable {
        private String companyName; // 公司名称
        private String position; // 职位
        private String startDate; // 开始时间
        private String endDate; // 结束时间
        private String jobDescription; // 工作描述
        private String achievements; // 工作成果
    }
    @Data
    public static class projectExperience implements Serializable {
        private String projectName; // 项目名称
        private String position; // 职位
        private String startDate; // 开始时间
        private String endDate; // 结束时间
        private String jobDescription; // 工作描述
        private String achievements; // 工作成果
    }
}
