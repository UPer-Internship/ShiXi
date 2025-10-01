package com.ShiXi.chat.service.impl;


import com.ShiXi.chat.entity.ChatMessage;
import com.ShiXi.chat.entity.Contact;
import com.ShiXi.chat.service.MessageService;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.ContactMapper;
import com.ShiXi.common.mapper.MessageMapper;
import com.ShiXi.common.mapper.UserMapper;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.common.utils.OSSUtil;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.common.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, ChatMessage> implements MessageService {
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private ContactMapper contactMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private OSSUploadService ossUploadService;

    // 聊天图片存储目录
    private static final String CHAT_IMAGE_DIR = "chat/images/";


    @Override
    public Result saveMessage(ChatMessage message) {
        message.setSendTime(LocalDateTime.now());
        Long userId = UserHolder.getUser().getId();
        message.setSenderId(userId);

        boolean save = save(message);
        if(save){
            updateLastContactTime(message.getReceiverId());
            //对方将当前用户标记为未读
            contactMapper.update(null,new UpdateWrapper<Contact>()
                    .eq("user_id", message.getReceiverId())
                    .eq("contact_user_id", userId)
                    .set("is_read", 0));
            return Result.ok();
        }
        return Result.fail("保存失败");
    }

    @Override
    public Result getMessagesBetweenUsers(Long userId2) {
        Long userId1 = UserHolder.getUser().getId();
        // 查询两个用户之间的所有消息（包括发送和接收的）
        List<ChatMessage> messages = messageMapper.selectList(new QueryWrapper<ChatMessage>()
                .and(wrapper -> wrapper.eq("sender_id", userId1).eq("receiver_id", userId2))
                .or(wrapper -> wrapper.eq("sender_id", userId2).eq("receiver_id", userId1))
                .orderByAsc("send_time")); // 按时间升序排列
        return Result.ok(messages);
    }


    private void buildContactBetweenUsers(Long userId2,String contactType) {
        Long userId1 = UserHolder.getUser().getId();
        //将两个用户设置为彼此的联系人
        if (!existsContactBetweenUsers(userId2)) {
            Contact contact1 = new Contact();
            contact1.setUserId(userId1);
            contact1.setContactUserId(userId2);
            contact1.setLastContactTime(LocalDateTime.now());
            contact1.setContactType(contactType);
            contactMapper.insert(contact1);
            Contact contact2 = new Contact();
            contact2.setUserId(userId2);
            contact2.setContactUserId(userId1);
            contact2.setLastContactTime(LocalDateTime.now());
            contact2.setContactType(contactType);
            contactMapper.insert(contact2);
        }
    }

    private boolean existsContactBetweenUsers(Long userId2) {
        Long userId1 = UserHolder.getUser().getId();
        return contactMapper.selectCount(new QueryWrapper<Contact>()
                .eq("user_id", userId1)
                .eq("contact_user_id", userId2)) > 0;
    }

    private void updateLastContactTime(Long userId2) {
        Long userId1 = UserHolder.getUser().getId();
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
    public Result getContactListByType(String contactType) {
        Long userId = UserHolder.getUser().getId();
        //查询联系人列表
        List<Contact> contacts = contactMapper.selectList(new QueryWrapper<Contact>()
                .eq("user_id", userId)
                .eq("contact_type", contactType)
                .eq("is_deleted", false)
                .orderByDesc("last_contact_time"));

        // 为每个联系人查询对应的用户信息
        for (Contact contact : contacts) {
            // 根据 contactUserId 查询用户信息
            User user = userMapper.selectById(contact.getContactUserId());
            if (user != null) {
                // 设置用户信息到联系人对象中
                contact.setContactUserName(user.getNickName());
                contact.setContactUserIcon(user.getIcon());
            }
        }
        return Result.ok(contacts);
    }


    @Override
    public Result markMessageAsRead(Long userId2) {
        Long userId1 = UserHolder.getUser().getId();
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
    public Result markContactAsRead(Long userId2,Integer status) {
        Long userId1 = UserHolder.getUser().getId();
        int result = contactMapper.update(null,new UpdateWrapper<Contact>()
                .eq("user_id", userId1)
                .eq("contact_user_id", userId2)
                .set("is_read", status));
        if(result==1){
            return Result.ok();
        }
        else{
            return Result.fail("标记失败");
        }
    }

    @Override
    public Result deleteContact(Long userId2){
        Long userId1 = UserHolder.getUser().getId();
        int result = contactMapper.update(null,new UpdateWrapper<Contact>()
                .eq("user_id", userId1)
                .eq("contact_user_id", userId2)
                .set("is_deleted", true));
        if(result==1){
            return Result.ok();
        }
        else{
            return Result.fail("删除失败");
        }
    }

    @Override
    public Result remarkContact(Long userId2, String remark) {
        Long userId1 = UserHolder.getUser().getId();
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

    @Override
    public Result addContactById(Long userId2,String contactType) {
        if (!existsContactBetweenUsers(userId2)) {
            buildContactBetweenUsers(userId2, contactType);
            return Result.ok("添加联系人成功");
        }
        return Result.fail("该用户已添加");
    }

    @Override
    public Result uploadChatImage(MultipartFile imageFile) {
        String url = ossUploadService.uploadPicture(imageFile,CHAT_IMAGE_DIR);
        if (url != null) {
            return Result.ok(url);
        } else {
            return Result.fail("上传图片失败");
        }
    }
}
