package com.ShiXi.Resume.resumeBrowsingHistory.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 简历浏览记录返回VO
 */
@Data
public class ResumeBrowsingHistoryVO {
    
    /**
     * 浏览记录ID
     */
    private Long id;
    
    /**
     * 简历ID
     */
    private Long resumeId;
    
    /**
     * 浏览时间
     */
    private LocalDateTime createTime;
}