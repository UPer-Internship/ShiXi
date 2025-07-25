package com.ShiXi.service.impl;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.ChatMessage;
import com.ShiXi.entity.Contact;
import com.ShiXi.mapper.ContactMapper;
import com.ShiXi.mapper.MessageMapper;
import com.ShiXi.service.MessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, ChatMessage> implements MessageService {
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private ContactMapper contactMapper;

    @Override
    public Result saveMessage(ChatMessage message) {
        message.setSendTime(LocalDateTime.now());
        boolean save = save(message);
        if(save){
            //自动添加联系人
            buildContactBetweenUsers(message.getSenderId(),message.getReceiverId());
            updateLastContactTime(message.getSenderId(),message.getReceiverId());
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

    private void buildContactBetweenUsers(Long userId1,Long userId2) {
        //将两个用户设置为彼此的联系人
        if (!existsContactBetweenUsers(userId1, userId2)) {
            Contact contact1 = new Contact();
            contact1.setUserId(userId1);
            contact1.setContactUserId(userId2);
            contact1.setLastContactTime(LocalDateTime.now());
            contact1.setIsBlocked(false);
            contactMapper.insert(contact1);
            Contact contact2 = new Contact();
            contact2.setUserId(userId2);
            contact2.setContactUserId(userId1);
            contact2.setLastContactTime(LocalDateTime.now());
            contact2.setIsBlocked(false);
            contactMapper.insert(contact2);
        }
    }

    private boolean existsContactBetweenUsers(Long userId1,Long userId2) {
        return contactMapper.selectCount(new QueryWrapper<Contact>().eq("user_id", userId1).eq("contact_user_id", userId2)) > 0;
    }

    private void updateLastContactTime(Long userId1, Long userId2) {
        // 更新 userId1 -> userId2 的 lastContactTime
        Contact contact1 = new Contact();
        contact1.setUserId(userId1);
        contact1.setContactUserId(userId2);
        contact1.setLastContactTime(LocalDateTime.now());
        contactMapper.update(contact1,
                new QueryWrapper<Contact>()
                        .eq("user_id", userId1)
                        .eq("contact_user_id", userId2));

        // 更新 userId2 -> userId1 的 lastContactTime
        Contact contact2 = new Contact();
        contact2.setUserId(userId2);
        contact2.setContactUserId(userId1);
        contact2.setLastContactTime(LocalDateTime.now());
        contactMapper.update(contact2,
                new QueryWrapper<Contact>()
                        .eq("user_id", userId2)
                        .eq("contact_user_id", userId1));
    }

    @Override
    public Result getContactList(Long userId) {
        List<Contact> contacts = contactMapper.selectList(new QueryWrapper<Contact>()
                .eq("user_id", userId)
                .eq("is_deleted", false));
        return Result.ok(contacts);
    }


    @Override
    public Result markMessageAsRead(Long userId1,Long userId2) {
        boolean result = update(null,new UpdateWrapper<ChatMessage>()
                .eq("sender_id", userId2)
                .eq("receiver_id", userId1)
                .set("is_read", true));

        if( result){
            return Result.ok();
        }
        else{
            return Result.fail("标记为已读失败");
        }
    }

    @Override
    public Result deleteMessage(Long messageId) {
        boolean result = update(null,new UpdateWrapper<ChatMessage>()
                .eq("id", messageId)
                .set("is_deleted", true));
        if( result){
            return Result.ok();
        }
        else{
            return Result.fail("删除失败");
        }
    }

    @Override
    public Result markContactAsRead(Long userId1, Long userId2) {
        boolean result = update(null,new UpdateWrapper<ChatMessage>()
                .eq("sender_id", userId1)
                .eq("receiver_id", userId2)
                .set("is_read", true));
        if( result){
            return Result.ok();
        }
        else{
            return Result.fail("标记为已读失败");
        }
    }

    @Override
    public Result deleteContact(Long userId1, Long userId2){
        boolean result = update(null,new UpdateWrapper<ChatMessage>()
                .eq("sender_id", userId1)
                .eq("receiver_id", userId2)
                .set("is_deleted", true));
        if( result){
            return Result.ok();
        }
        else{
            return Result.fail("删除失败");
        }
    }

    @Override
    public Result remarkContact(Long userId1, Long userId2, String remark) {
        try {
            Contact contact = contactMapper.selectOne(new QueryWrapper<Contact>()
                    .eq("user_id", userId1)
                    .eq("contact_user_id", userId2));
            if (contact != null) {
                contact.setRemark(remark);
                contactMapper.updateById(contact);
                return Result.ok();
            } else {
                return Result.fail("联系人不存在");
            }
        } catch (Exception e) {
            return Result.fail("修改备注失败");
        }
    }
}
