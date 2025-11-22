package com.ShiXi.settings.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("settings")
public class Settings implements Serializable {
    private static final long serialVersionUID = 1L;
    ;
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 关联用户ID
     */
    private Long userId;

    /**
     * 陌生人隐私权限：
     * 0 = 陌生人无法查看主页
     * 1 = 可以查看但无法添加好友
     * 2 = 对陌生人友好
     */
    private Integer privacyStranger = 2;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
