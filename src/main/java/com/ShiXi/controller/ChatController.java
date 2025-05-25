package com.ShiXi.controller;

import com.ShiXi.entity.ChatMessage;
import com.ShiXi.entity.OutputMessage;
import com.ShiXi.utils.NativeWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 替代原来的 ChatController（去掉 @MessageMapping 和 STOMP 相关逻辑）
 */
@RestController
public class ChatController {

    @Resource
    private ObjectMapper objectMapper; // 用于 JSON 序列化

    /**
     * 接收前端发送的消息，并通过 WebSocket 发送给目标用户
     */
    @PostMapping("/chat/sendMessage")
    public void sendMessage(@RequestBody ChatMessage chatMessage) {
        try {
            // 构造输出消息
            OutputMessage outputMessage = new OutputMessage(
                    chatMessage.getContent(),
                    String.valueOf(chatMessage.getSenderId()),
                    new Date().toString()
            );

            // 转换为 JSON 字符串
            String messageJson = objectMapper.writeValueAsString(outputMessage);

            // 调用原生 WebSocket 处理器发送消息给指定用户
            NativeWebSocketHandler.sendToUser(chatMessage.getReceiverId(), messageJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
