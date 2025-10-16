package com.ShiXi.position.common.domin.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 岗位浏览记录返回VO
 */
@Data
public class JobBrowsingHistoryVO {
    
    /**
     * 浏览记录ID
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
     * 浏览时间
     */
    private LocalDateTime createTime;
}