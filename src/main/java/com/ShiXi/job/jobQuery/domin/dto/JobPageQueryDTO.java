package com.ShiXi.job.jobQuery.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询岗位的查询类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPageQueryDTO {
    private Integer page;//页码
    private Integer pageSize;//每页记录数
    private String type;//岗位类型(实现、兼职、科研课题)
    private String category;//岗位类别(UI设计等tag)
    private String industry;      // 行业（如互联网、金融等）
    private Double salaryMin;     // 薪资下限
    private Double salaryMax;     // 薪资上限
    private String onboardTime;   // 到岗时间（如立即、1周内等）
    private String tag;           // 岗位标签，如“远程-线下-可转正”
    private String totalTime;     // 实习总时长
}
