package com.ShiXi.service;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.ChatMessage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface MessageService extends IService<ChatMessage> {

    Result saveMessage(ChatMessage message);

    Result getMessagesBetweenUsers(Long userId1,Long userId2);

    Result buildContactBetweenUsers(Long userId1,Long userId2);

    boolean existsContactBetweenUsers(Long userId1,Long userId2);

    void updateLastContactTime(Long userId1,Long userId2);

    Result getContactList(Long userId);
}
