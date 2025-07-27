package com.ShiXi.service.impl;

import com.ShiXi.template.FeishuMessage;
import com.ShiXi.template.RichTextMessage;
import com.ShiXi.template.TextMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FeishuService {
    private static final Logger logger = LoggerFactory.getLogger(FeishuService.class);

    @Value("${feishu.webhook.url}")
    private String webhookUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public FeishuService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 发送文本消息
     */
    public void sendTextMessage(String content) {
        TextMessage message = new TextMessage(content);
        sendMessage(message);
    }

    /**
     * 发送富文本消息
     */
    public void sendRichTextMessage(String title, String... contents) {
        RichTextMessage message = new RichTextMessage();
        message.getContent().getZh_cn().setTitle(title);

        for (String content : contents) {
            message.addContent(content);
        }

        sendMessage(message);
    }

    /**
     * 发送消息核心方法
     */
    private void sendMessage(FeishuMessage message) {
        if (webhookUrl == null || webhookUrl.isEmpty()) {
            logger.warn("飞书Webhook地址未配置，无法发送消息");
            return;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String jsonBody = objectMapper.writeValueAsString(message);
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    webhookUrl,
                    request,
                    String.class
            );

            if (response.getStatusCode() != HttpStatus.OK) {
                logger.error("发送飞书消息失败，响应码: {}", response.getStatusCodeValue());
            } else {
                logger.info("飞书消息发送成功");
            }
        } catch (JsonProcessingException e) {
            logger.error("飞书消息序列化失败", e);
        } catch (Exception e) {
            logger.error("发送飞书消息时发生异常", e);
        }
    }
}
