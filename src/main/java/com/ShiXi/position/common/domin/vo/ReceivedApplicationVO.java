package com.ShiXi.position.common.domin.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 收到的投递记录VO
 */
@Data
public class ReceivedApplicationVO {
    
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
     * 投递者ID
     */
    private Long applicantId;
    
    /**
     * 投递状态
     */
    private String status;
    
    /**
     * 是否已读
     */
    private Integer isRead;
    
    /**
     * 投递时间
     */
    private LocalDateTime createTime;
    
    /**
     * 投递附言
     */
    private String message;
    
    // ========== 简历完整信息 ==========
    
    /**
     * 简历ID
     */
    private Long resumeId;
    
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
     * 微信
     */
    private String wechat;
    
    /**
     * 个人优势
     */
    private String advantages;
    
    /**
     * 期望职位
     */
    private String expectedPosition;
    
    /**
     * 教育经历
     */
    private String educationExperiences;
    
    /**
     * 工作实习经历
     */
    private String workAndInternshipExperiences;
    
    /**
     * 项目经历
     */
    private String projectExperiences;
    
    /**
     * 附件简历URL
     */
    private String resumeLink;
    
    /**
     * 简历创建时间
     */
    private LocalDateTime resumeCreateTime;
    
    /**
     * 简历更新时间
     */
    private LocalDateTime resumeUpdateTime;
}