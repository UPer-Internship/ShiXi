package com.ShiXi.common.domin.vo;

import lombok.Data;

import java.util.List;

@Data
public class MajorVO {
    private List<SecondLevelCategoryLabel> majors;

    @Data
    public static class SecondLevelCategoryLabel {
        private String secondLevelCategoryLabel;
        private List<FirstLevelCategoryLabel> firstLevelCategoryLabel;
    }
    @Data
    public static class FirstLevelCategoryLabel {
        private String firstLevelCategoryLabel;
        private List<String> major;
    }
}
