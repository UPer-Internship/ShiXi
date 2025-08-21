package com.ShiXi.team.domin.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TeamMemberVO {
    private Long id; // 成员记录ID
    private Long userId; // 用户ID
    private String userName; // 用户姓名
    private String userIcon; // 用户头像
    private String role; // 成员角色
    private String responsibility; // 成员职责描述
    private String applyReason; // 申请说明
    private LocalDateTime joinTime; // 加入时间
    private String status; // 申请状态
}