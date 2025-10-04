package com.ShiXi.position.common.domin.dto;

import lombok.Data;

/**
 * 投递记录分页查询DTO
 */
@Data
public class ApplicationPageQueryDTO {
    
    /**
     * 页码，默认为1
     */
    private Integer page = 1;
    
    /**
     * 每页大小，默认为10
     */
    private Integer pageSize = 10;
    
    /**
     * 投递状态筛选：待处理、已接受、已拒绝
     */
    private String status;
    
    /**
     * 岗位类型筛选：正职、兼职、实习
     */
    private String positionType;
}