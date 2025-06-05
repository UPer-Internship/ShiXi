package com.ShiXi.service.impl;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.ChatMessage;
import com.ShiXi.mapper.MessageMapper;
import com.ShiXi.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, ChatMessage> implements MessageService {
    @Resource
    private MessageMapper messageMapper;

    @Override
    public Result saveMessage(ChatMessage message) {
        message.setSendTime(LocalDateTime.now());
        boolean save = save(message);
        if(save){
            return Result.ok();
        }
        return Result.fail("保存失败");
    }

    @Override
    public Result getMessagesBetweenUsers(Long userId1,Long userId2){
        //查询这两个用户之间的消息
        List<ChatMessage> messages = messageMapper.selectList(null);
        return Result.ok(messages);
    }
}
