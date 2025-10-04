package com.ShiXi.position.common.domin.dto;

import lombok.Data;

/**
 * 岗位投递请求DTO
 */
@Data
public class JobApplicationDTO {
    
    /**
     * 岗位ID
     */
    private Long positionId;
    
    /**
     * 岗位类型：正职、兼职、实习
     */
    private String positionType;
    
    /**
     * 投递附言
     */
    private String message;
}