package com.ShiXi.controller;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.ChatMessage;
import com.ShiXi.entity.OutputMessage;
import com.ShiXi.service.MessageService;
import com.ShiXi.utils.NativeWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 替代原来的 ChatController（去掉 @MessageMapping 和 STOMP 相关逻辑）
 */
@Slf4j
@RestController
@Api(tags = "聊天相关接口")
public class ChatController {

    @Resource
    private ObjectMapper objectMapper; // 用于 JSON 序列化
    @Resource
    private MessageService messageService;

    /**
     * 接收前端发送的消息，并通过 WebSocket 发送给目标用户
     */
    @PostMapping("/chat/sendMessage")
    @ApiOperation("发送消息")
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


    @PostMapping("/message/saveMessage")
    @ApiOperation("保存消息到数据库")
    public Result saveMessage(@RequestBody ChatMessage message){
        return messageService.saveMessage(message);
    }

    @GetMapping("/message/getMessages")
    @ApiOperation("获取历史消息")
    public Result getMessages(@RequestParam Long userId1, @RequestParam Long userId2){
        return messageService.getMessagesBetweenUsers(userId1,userId2);
    }
}
