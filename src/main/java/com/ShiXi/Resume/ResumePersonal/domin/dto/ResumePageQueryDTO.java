package com.ShiXi.Resume.ResumePersonal.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询简历的查询类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumePageQueryDTO {
    private Integer page; // 页码
    private Integer pageSize; // 每页记录数
    private String expectedPosition; // 期望职位，用于模糊查询
}