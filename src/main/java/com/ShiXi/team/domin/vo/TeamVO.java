package com.ShiXi.team.domin.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TeamVO {
    private Long id; // 团队ID
    private String uuid; // 团队UUID
    private String name; // 团队名称
    private String description; // 团队描述
    private Long leaderId; // 团队创建者ID
    private String leaderName; // 团队创建者姓名
    private String school; // 团队所属院校
    private String industry; // 团队所属行业
    private LocalDateTime createTime; // 创建时间
    private Long memberCount; // 成员数量
    private List<TeamMemberVO> members; // 团队成员列表
}