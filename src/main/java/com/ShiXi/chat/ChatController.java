package com.ShiXi.chat;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.chat.entity.ChatMessage;
import com.ShiXi.chat.entity.OutputMessage;
import com.ShiXi.chat.service.MessageService;
import com.ShiXi.common.utils.NativeWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@RestController
@Tag(name = "聊天相关接口")
public class ChatController {

    @Resource
    private ObjectMapper objectMapper; // 用于 JSON 序列化
    @Resource
    private MessageService messageService;

    /**
     * 接收前端发送的消息，并通过 WebSocket 发送给目标用户
     */
    @PostMapping("/chat/sendMessage")
    @Operation(summary = "发送消息")
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
    @Operation(summary = "保存消息到数据库")
    public Result saveMessage(@RequestBody ChatMessage message){
        return messageService.saveMessage(message);
    }

    @GetMapping("/message/getMessages")
    @Operation(summary = "获取历史消息")
    public Result getMessages(@RequestParam Long userId2){
        return messageService.getMessagesBetweenUsers(userId2);
    }

    @PostMapping("/message/markAsRead")
    @Operation(summary = "标记为已读")
    public Result markAsRead(@RequestParam Long userId2){
        return messageService.markMessageAsRead(userId2);
    }

    @PostMapping("/message/delete")
    @Operation(summary = "删除消息")
    public Result deleteMessage(@RequestParam Long messageId){
        return messageService.deleteMessage(messageId);
    }

    @GetMapping("/contact/getList")
    @Operation(summary = "获取联系人列表")
    public Result getContactListByType(@RequestParam String contactType){
        return messageService.getContactListByType(contactType);
    }

    @PostMapping("/contact/markReadStatus")
    @Operation(summary = "标记联系人状态")
    public Result markContactAsRead(@RequestParam Long userId2,@RequestParam Integer status){
        return messageService.markContactAsRead(userId2,status);
    }

    @PostMapping("/contact/delete")
    @Operation(summary = "删除联系人")
    public Result deleteContact(@RequestParam Long userId2){
        return messageService.deleteContact(userId2);
    }

    @PostMapping("/contact/remark")
    @Operation(summary = "备注联系人")
    public Result remarkContact(@RequestParam Long userId2,@RequestParam String remark){
        return messageService.remarkContact(userId2,remark);
    }

    @PostMapping("/contact/addContact")
    @Operation(summary = "添加联系人")
    public Result addContactById(@RequestParam Long userId2,@RequestParam String contactType){
        return messageService.addContactById(userId2,contactType);
    }

    @PostMapping("/chat/uploadImage")
    @Operation(summary = "上传聊天图片")
    public Result uploadChatImage(@RequestParam("imageFile") MultipartFile imageFile){
        return messageService.uploadChatImage(imageFile);
    }
}
