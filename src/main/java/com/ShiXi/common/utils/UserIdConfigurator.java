package com.ShiXi.common.utils;

import cn.hutool.core.util.StrUtil;
import com.ShiXi.common.mapper.UserMapper;
import com.ShiXi.user.common.entity.User;

import javax.annotation.Resource;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;
import java.util.Arrays;

public class UserIdConfigurator extends Configurator {

    @Resource
    private UserMapper userMapper;

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // 获取 URL 查询参数
        String query = request.getRequestURI().getQuery();

        if (query != null) {
            Arrays.stream(query.split("&"))
                    .map(param -> param.split("="))
                    .filter(kv -> kv.length == 2 && "userId".equals(kv[0]))
                    .findFirst()
                    .ifPresent(kv -> {
                        try {
                            String uuid = kv[1];
                            if (StrUtil.isNotBlank(uuid)) {
                                // 根据 uuid 查询用户
                                User user = userMapper.selectOne(
                                        new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<User>()
                                                .eq(User::getUuid, uuid)
                                );
                                if (user != null) {
                                    sec.getUserProperties().put("userId", user.getId());
                                }
                            }
                        } catch (NumberFormatException ignored) {
                        }
                    });
        }
    }
}
