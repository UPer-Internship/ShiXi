package com.ShiXi.user.IdentityAuthentication.common.domin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class KeyAndWaitForAuditingUserDTO {
    private Long waitingForAuditingUserId;
    private Integer identification;
    private String key;
}
