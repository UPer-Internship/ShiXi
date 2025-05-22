package com.ShiXi.controller;

import com.ShiXi.entity.ChatMessage;
import com.ShiXi.entity.OutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public OutputMessage sendMessage(@Payload ChatMessage message){
        return new OutputMessage(message.getContent(), message.getSender(), new java.util.Date().toString());
    }
}
