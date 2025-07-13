package com.ShiXi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信小程序相关信息
 */
@Component
@ConfigurationProperties(prefix = "shixi.wechat")
@Data
public class WeChatProperties {
    private String appid; //小程序的appid
    private String secret; //小程序的秘钥
}
