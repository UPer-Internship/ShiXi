package com.ShiXi.position.common.domin.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 投递记录返回VO
 */
@Data
public class JobApplicationVO {
    
    /**
     * 投递记录ID
     */
    private Long id;
    
    /**
     * 岗位ID
     */
    private Long positionId;
    
    /**
     * 岗位类型
     */
    private String positionType;
    
    /**
     * 投递状态
     */
    private String status;
    
    /**
     * 投递时间
     */
    private LocalDateTime createTime;
    
    /**
     * 投递附言
     */
    private String message;
}