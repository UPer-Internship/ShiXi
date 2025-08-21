package com.ShiXi.team.domin.dto;

import lombok.Data;

@Data
public class ApproveJoinDTO {
    private Long teamMemberId; // 团队成员申请ID
    private String status; // 审核状态：approved-通过，rejected-拒绝
}