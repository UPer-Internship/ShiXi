//package com.ShiXi.common.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
//
//
//@Configuration
//@EnableSwagger2WebMvc // 启用 Swagger 2.0（Knife4j 3.x 需此注解）
//public class Knife4jConfig {
//
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                // 扫描指定包下的接口（替换为你的 Controller 包路径）
//                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
//                .paths(PathSelectors.any()) // 匹配所有路径
//                .build();
//    }
//
//    // 文档基础信息（标题、描述、版本等）
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Spring Boot 2.7.18 接口文档")
//                .description("基于 Knife4j + Swagger 2.0 构建")
//                .version("1.0.0")
//                .build();
//    }
//}