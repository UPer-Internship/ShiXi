package com.ShiXi.team.domin.dto;

import lombok.Data;

@Data
public class CreateTeamDTO {
    private String name; // 团队名称
    private String description; // 团队描述
    private String school; // 团队所属院校
    private String industry; // 团队所属行业
}