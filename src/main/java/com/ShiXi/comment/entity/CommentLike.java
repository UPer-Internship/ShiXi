package com.ShiXi.comment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comment_like")
public class CommentLike {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long commentId;
    private Long replyId;
    private LocalDateTime createTime;
}
