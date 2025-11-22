package com.ShiXi.position.common.domin.dto;

import lombok.Data;

/**
 * 岗位浏览记录请求DTO
 */
@Data
public class JobBrowsingHistoryDTO {
    
    /**
     * 岗位ID
     */
    private Long positionId;
    
    /**
     * 岗位类型：正职、兼职、实习
     */
    private String positionType;
}