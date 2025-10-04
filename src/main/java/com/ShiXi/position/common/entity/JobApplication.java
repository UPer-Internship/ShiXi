package com.ShiXi.position.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 岗位投递记录实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("job_application")
public class JobApplication implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 投递者ID
     */
    private Long applicantId;
    
    /**
     * 岗位发布者ID
     */
    private Long publisherId;
    
    /**
     * 岗位ID
     */
    private Long positionId;
    
    /**
     * 岗位类型：正职、兼职、实习
     */
    private String positionType;
    
    /**
     * 投递状态：待处理、已接受、已拒绝
     */
    private String status;
    
    /**
     * 是否已读：0-未读，1-已读
     */
    private Integer isRead;
    
    /**
     * 投递附言
     */
    private String message;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer isDeleted; // 逻辑删除标志，0-未删除，1-已删除
}