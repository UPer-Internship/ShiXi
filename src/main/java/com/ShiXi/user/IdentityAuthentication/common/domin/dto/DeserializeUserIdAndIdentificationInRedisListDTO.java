package com.ShiXi.user.IdentityAuthentication.common.domin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeserializeUserIdAndIdentificationInRedisListDTO {
    private Long userId;
    private String identification;
}
