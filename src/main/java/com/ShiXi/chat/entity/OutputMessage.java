package com.ShiXi.chat.entity;

import lombok.Data;

@Data
public class OutputMessage {
    private String content;
    private String sender;
    private String time; // 使用字符串格式化时间以便于前端处理
    private Long receiver;  // 接收者ID
    private String type;  // 消息类型


    public OutputMessage(String content, String sender, String time, Long receiver, String type) {
        this.content = content;
        this.sender = sender;
        this.time = time;
        this.receiver = receiver;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

