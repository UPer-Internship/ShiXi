package com.ShiXi.position.common.entity;

import lombok.Data;

@Data
public class JobCategory {
    private Long id; // 主键ID
    private String category; // 岗位类别名称
    private String description; // 岗位类别描述
    private String firstLevelCategoryLabel; // 一级分类标签
}