package com.ShiXi.service;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.ChatMessage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface MessageService extends IService<ChatMessage> {

    /**
     * 发送消息
     * @param message 消息对象
     * @return
     */
    Result saveMessage(ChatMessage message);

    /**
     * 获取消息列表
     * @param userId1 其中一个用户
     * @param userId2 另一个用户
     * @return
     */
    Result getMessagesBetweenUsers(Long userId1,Long userId2);

    /**
     * 获取联系人列表
     * @param userId 用户id
     * @return
     */
    Result getContactList(Long userId);

    /**
     * 标记消息为已读
     * @param userId1 当前用户id
     * @param userId2 联系人id
     * @return
     */
    Result markMessageAsRead(Long userId1, Long userId2);

    /**
     * 删除消息
     * @param messageId 消息id
     * @return
     */
    Result deleteMessage(Long messageId);

    /**
     * 标记该联系人为已读
     * @param userId1 当前用户id
     * @param userId2 联系人id
     * @return
     */
    Result markContactAsRead(Long userId1, Long userId2);

    /**
     * 删除联系人
     * @param userId1 当前用户id
     * @param userId2 联系人id
     * @return
     */
    Result deleteContact(Long userId1, Long userId2);

    /**
     * 添加备注
     * @param userId1
     * @param userId2
     * @param remark 备注内容
     * @return
     */
    Result remarkContact(Long userId1, Long userId2, String remark);
}
