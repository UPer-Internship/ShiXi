package com.ShiXi.common.utils;

import com.ShiXi.chat.entity.ChatMessage;
import com.ShiXi.chat.entity.OutputMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 原生 WebSocket 处理器（使用 javax.websocket）
 */
@Component
@ServerEndpoint(value="/ws",  configurator = UserIdConfigurator.class)
public class NativeWebSocketHandler {

    // 所有连接的客户端
    private static final CopyOnWriteArraySet<NativeWebSocketHandler> webSockets = new CopyOnWriteArraySet<>();
    // 用户ID到Session的映射（可用于私聊）
    private static final ConcurrentHashMap<Long, Session> userSessions = new ConcurrentHashMap<>();

    private Session session;
    private Long userId; // 当前用户ID

    @OnOpen
    public void onOpen(Session session) {
        // 获取 UserId 的逻辑在 configurator 中已处理，无需再传入 config
        this.session = session;

        // 由于我们使用的是自定义 configurator，userId 已被放入 userProperties
        // 所以可以直接通过反射或工具类获取 userId
        try {
            Object userIdObj = session.getUserProperties().get("userId");
            if (userIdObj instanceof Long) {
                this.userId = (Long) userIdObj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        webSockets.add(this);

        if (this.userId != null) {
            userSessions.put(this.userId, session);
            System.out.println("【WebSocket】新连接加入：" + session.getId() + "，用户ID：" + this.userId);
        } else {
            System.out.println("【WebSocket】匿名连接加入：" + session.getId());
        }
    }



    @OnClose
    public void onClose() {
        webSockets.remove(this);
        if (userId != null) {
            userSessions.remove(userId); // 移除用户映射
            System.out.println("【WebSocket】用户断开连接：" + userId);
        }
        System.out.println("【WebSocket】连接关闭：" + session.getId());
    }


    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ChatMessage chatMessage = mapper.readValue(message, ChatMessage.class);

            Long senderId = chatMessage.getSenderId();
            Long receiverId = chatMessage.getReceiverId();

            // 构造输出消息
            OutputMessage outputMessage = new OutputMessage(
                    chatMessage.getContent(),
                    String.valueOf(senderId),
                    new java.util.Date().toString()
            );

            // 广播给所有连接的客户端（可改为定向发送）
            String response = mapper.writeValueAsString(outputMessage);

            // 私聊逻辑：查找接收者是否在线
            Session receiverSession = userSessions.get(receiverId);
            if (receiverSession != null && receiverSession.isOpen()) {
                receiverSession.getBasicRemote().sendText(response);
            } else {
                // 可选：离线消息存储或丢弃
                System.out.println("用户 " + receiverId + " 不在线，消息未送达");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.err.println("【WebSocket】发生错误：" + error.getMessage());
        error.printStackTrace();
    }

    // 发送消息给指定用户的方法
    public static void sendToUser(Long userId, String message) {
        Session session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
