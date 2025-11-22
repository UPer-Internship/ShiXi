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
@TableName("comment_reply")
public class CommentReply {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String image;
    private Long commentId;          // 关联的一级评论ID
    private Long userId;             // 回复者ID
    private Long toUserId;           // 被回复者ID（可为null，若回复评论本身则指向评论者）
    private String content;          // 回复内容
    private Integer likeCount;       // 点赞数（冗余字段）
    private LocalDateTime createTime;// 回复时间
    private LocalDateTime updateTime;
}
