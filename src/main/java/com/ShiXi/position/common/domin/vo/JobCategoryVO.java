package com.ShiXi.position.common.domin.vo;

import lombok.Data;

import java.util.List;

@Data
public class JobCategoryVO {
    private List<FirstLevelCategoryLabel> categories;

    @Data
    public static class FirstLevelCategoryLabel {
        private String firstLevelCategoryLabel; // 一级分类标签
        private List<Category> category; // 该一级分类下的岗位类别列表
    }

    @Data
    public static class Category {
        private Long id; // 主键ID
        private String category; // 岗位类别名称
        private String description; // 岗位类别描述
    }
}