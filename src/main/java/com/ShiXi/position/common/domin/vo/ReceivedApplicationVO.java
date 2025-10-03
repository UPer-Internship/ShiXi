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
     * 岗位标题
     */
    private String positionTitle;
    
    /**
     * 投递者ID
     */
    private Long applicantId;
    
    /**
     * 投递者姓名
     */
    private String applicantName;
    
    /**
     * 投递者性别
     */
    private String gender;
    
    /**
     * 投递者头像
     */
    private String avatar;
    
    /**
     * 投递者手机号
     */
    private String phone;
    
    /**
     * 投递者微信
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
}