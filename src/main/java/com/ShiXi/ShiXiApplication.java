package com.ShiXi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@MapperScan("com.xl.mapper")
@SpringBootApplication
@MapperScan("com.ShiXi.common.mapper")
@EnableScheduling
public class ShiXiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiXiApplication.class, args);
    }
}
