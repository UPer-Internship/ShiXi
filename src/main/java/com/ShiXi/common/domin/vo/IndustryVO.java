package com.ShiXi.common.domin.vo;

import lombok.Data;

import java.util.List;

@Data
public class IndustryVO {
    private List<FirstLevelCategoryLabel> industries;

    @Data
    public static class FirstLevelCategoryLabel {
        private String firstLevelCategoryLabel;
        private List<String> industry;
    }
}