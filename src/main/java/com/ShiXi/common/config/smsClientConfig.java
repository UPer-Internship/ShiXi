package com.ShiXi.common.config;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

// 标记为Spring组件，让Spring扫描并管理实例
@Component
public class smsClientConfig {

    // 1. 去掉static，改为非静态成员变量
    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;

    // 2. 提供静态getter方法，通过类实例获取注入的值
    private static smsClientConfig instance;

    // 3. Spring初始化时自动调用，将实例赋值给静态变量
    @PostConstruct
    public void init() {
        instance = this;
    }

    // 4. 静态方法中通过instance获取注入的非静态变量
    public static Client createClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(instance.accessKeyId)  // 通过实例获取
                .setAccessKeySecret(instance.accessKeySecret);  // 通过实例获取
        config.endpoint = "dysmsapi.aliyuncs.com";

        return new Client(config);
    }
}