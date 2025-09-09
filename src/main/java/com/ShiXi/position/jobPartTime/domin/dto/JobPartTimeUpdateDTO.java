package com.ShiXi.position.jobPartTime.domin.dto;

import lombok.Data;

import java.util.List;

@Data
public class JobPartTimeUpdateDTO {
    private Long id; // 岗位id
    
    private Long companyId; // 关联的公司id
    
    private String title; // 标题
    
    private Double salaryMin; // 薪水下限（单位元）
    
    private Double salaryMax; // 薪水上限（单位元）
    
    private String mainText; // 正文
    
    private String province; // 公司所在省份
    
    private String city; // 城市
    
    private String district; // 区县
    
    private String type; // 正职/兼职/实习
    
    private List<String> tag; // 标签列表
    
    private Integer status; // 状态 0/1 可见/不可见
    
    private String category; // 职位
    
    private String financingProgress; // 融资进度
    
    private String enterpriseScale; // 企业规模
    
    private String industry; // 企业所在行业
}