package com.ShiXi.position.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("position_favorite")
public class PositionFavorite implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Long positionId;
    
    private String type; // 岗位类型：正职、兼职、实习

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableLogic
    private Integer isDeleted; // 逻辑删除标志，0-未删除，1-已删除
}