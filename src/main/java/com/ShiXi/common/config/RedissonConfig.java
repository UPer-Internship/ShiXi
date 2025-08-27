package com.ShiXi.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.username:}")
    private String redisUsername;

    @Value("${spring.redis.password:}")
    private String redisPassword;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String address = "redis://" + redisHost + ":" + redisPort;

        if (redisUsername != null && !redisUsername.isEmpty()) {
            config.useSingleServer()
                    .setAddress(address)
                    .setUsername(redisUsername)
                    .setPassword(redisPassword);
        } else {
            config.useSingleServer()
                    .setAddress(address)
                    .setPassword(redisPassword);
        }

        return Redisson.create(config);
    }
}
