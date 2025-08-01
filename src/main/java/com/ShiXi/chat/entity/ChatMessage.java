package com.ShiXi.chat.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("message")
public class ChatMessage {
    private String id;
    private String content;
    private Long senderId;
    private Long receiverId;
    private LocalDateTime sendTime;
    @TableLogic
    private Integer isDeleted; // 逻辑删除标志，0-未删除，1-已删除
    private Integer isRead;
}

