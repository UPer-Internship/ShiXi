package com.ShiXi.config;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSSClientConfig {
    // 从配置文件读取 OSS 信息

    @Value("${spring.oss.endpoint}")
    private String endpoint;

    @Value("${spring.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${spring.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${spring.oss.bucketName}")
    private String bucketName;

    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}