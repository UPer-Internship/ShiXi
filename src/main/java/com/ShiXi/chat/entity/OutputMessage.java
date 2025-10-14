package com.ShiXi.chat.entity;

import lombok.Data;

@Data
public class OutputMessage {
    private String content;
    private Long senderId;
    private String time; // 使用字符串格式化时间以便于前端处理
    private Long receiverId;  // 接收者ID
    private String type;  // 消息类型


    public OutputMessage(String content, long senderId, String time, Long receiverId, String type) {
        this.content = content;
        this.senderId = senderId;
        this.time = time;
        this.receiverId = receiverId;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

