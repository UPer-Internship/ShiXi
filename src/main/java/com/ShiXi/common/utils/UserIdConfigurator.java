package com.ShiXi.common.utils;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;
import java.util.Arrays;

public class UserIdConfigurator extends Configurator {

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
                            Long userId = Long.parseLong(kv[1]);
                            sec.getUserProperties().put("userId", userId);
                        } catch (NumberFormatException ignored) {
                        }
                    });
        }
    }
}
