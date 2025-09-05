package com.ShiXi.job.jobQuery.domin.dto;

import lombok.Data;

@Data
public class EsJobQueryDTO {
    private String title;
    private Double salaryMin; // 薪水下限
    private Double salaryMax; // 薪水上限
    private String financingProgress;//A轮融资 D轮 不需要
    private String enterpriseScale;//10000人 100人
    private String category;//UI设计 游戏策划 ...
    private String type;//实习 兼职 课题
}
