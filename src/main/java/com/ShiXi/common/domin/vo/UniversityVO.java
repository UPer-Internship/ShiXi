package com.ShiXi.common.domin.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UniversityVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String universityName;
}