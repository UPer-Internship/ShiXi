package com.ShiXi.common.domin.vo;

import lombok.Data;

import java.util.List;

@Data
public class RegionVO {
    private List<Provinces> regions;

    @Data
    public static class Provinces {
        private String province;
        private List<cities> cities;
    }
    @Data
    public static class cities {
        private String city;
        private List<String> district;
    }
}
