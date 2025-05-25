package com.ShiXi.entity;

import lombok.Data;

@Data
public class ChatMessage {
    private String content;
    private Long senderId;
    private Long receiverId;
}

