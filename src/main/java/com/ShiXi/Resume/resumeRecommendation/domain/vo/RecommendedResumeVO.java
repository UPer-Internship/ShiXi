package com.ShiXi.Resume.resumeRecommendation.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 推荐简历VO类
 */
@Data
public class RecommendedResumeVO {
    
    /**
     * 简历ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 性别
     */
    private String gender;
    
    /**
     * 出生日期
     */
    private String birthDate;
    
    /**
     * 微信号
     */
    private String wechat;
    
    /**
     * 期望职位
     */
    private String expectedPosition;
    
    /**
     * 个人优势
     */
    private String advantages;
    
    /**
     * 简历附件链接
     */
    private String resumeLink;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}