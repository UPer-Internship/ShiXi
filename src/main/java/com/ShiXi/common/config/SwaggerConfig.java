//package com.ShiXi.common.config;
//
//import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@Configuration
//@EnableOpenApi
//@EnableKnife4j
//public class SwaggerConfig {
//    @Bean
//    public Docket createRestApi() {
//        return (new Docket(DocumentationType.OAS_30))
//                .apiInfo(apiInfo())
//                .enable(true)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.ShiXi"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return (new ApiInfoBuilder())
//                .title("4Uper-Up 接口说明")
//                        .description("接口说明")
//                                .termsOfServiceUrl("http://localhost:8081/")
//                                .version("1.0.0")
//                                .build();
//    }
//}
