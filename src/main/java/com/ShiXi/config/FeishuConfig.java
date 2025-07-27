package com.ShiXi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FeishuConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
