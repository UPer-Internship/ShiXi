package com.ShiXi.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OutputMessage {
    private String content;
    private String sender;
    private String time; // 使用字符串格式化时间以便于前端处理

    public OutputMessage(String content, String sender, String time) {
        this.content = content;
        this.sender = sender;
        this.time = time;
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
}

