package com.ShiXi.application.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("application")
public class Application implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private Long enterpriseId;
    private Long jobId;
    private String resumeUrl;
    private String message;
    private String status; // pending, accepted, rejected
    private Integer isRead;  // 0: 未读, 1: 已读
    @TableLogic
    private Integer isDeleted;  // 0: 未删除, 1: 已删除
    private LocalDateTime applyTime;
    private LocalDateTime updateTime;
}
