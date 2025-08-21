package com.ShiXi.team.domin.dto;

import lombok.Data;

@Data
public class JoinTeamDTO {
    private String teamUuid; // 团队UUID
    private String responsibility; // 成员职责描述
    private String applyReason; // 申请说明
}