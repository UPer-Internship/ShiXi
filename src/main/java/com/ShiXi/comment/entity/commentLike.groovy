package com.ShiXi.comment.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data
import lombok.EqualsAndHashCode
import lombok.experimental.Accessors

import java.time.LocalDateTime

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comment_like")
class commentLike {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private Long userId;

    private Long commentId;

    private LocalDateTime createTime;
}
