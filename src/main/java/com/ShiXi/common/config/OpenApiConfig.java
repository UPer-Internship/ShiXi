package com.ShiXi.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("实习管理系统 API 文档")
                        .version("1.0.0")
                        .description("基于 Spring Boot 2.7.18 + OpenAPI 3.0 的接口文档"));
    }
}
