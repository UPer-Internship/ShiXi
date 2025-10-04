package com.ShiXi.position.common.domin.dto;

import lombok.Data;

/**
 * 根据岗位查询投递简历DTO
 */
@Data
public class PositionApplicationQueryDTO {
    
    /**
     * 岗位ID
     */
    private Long positionId;
    
    /**
     * 岗位类型：正职、兼职、实习
     */
    private String positionType;
    
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
}