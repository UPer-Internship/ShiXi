package com.ShiXi.Resume.ResumePersonal.domin.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 简历公开信息VO  用于分页查询，只包含非敏感信息
 */
@Data
public class ResumePublicVO implements Serializable {
    private Long id; // 简历ID
    private String name; // 姓名
    private String advantages; // 个人优势
    private List<String> expectedPosition; // 期望职位
    
    // 教育经历
    private List<ResumeVO.educationExperience> educationExperiences;
    
    // 工作和实习经历
    private List<ResumeVO.workAndInternshipExperience> workAndInternshipExperiences;
    
    // 项目经历
    private List<ResumeVO.projectExperience> projectExperiences;
}