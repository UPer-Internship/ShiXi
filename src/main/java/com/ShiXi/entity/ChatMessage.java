package com.ShiXi.entity;

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
}

