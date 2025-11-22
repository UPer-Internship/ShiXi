package com.ShiXi.Resume.resumeFavorite.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("resume_favorite")
public class ResumeFavorite implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Long resumeId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableLogic
    private Integer isDeleted; // 逻辑删除标志，0-未删除，1-已删除
}