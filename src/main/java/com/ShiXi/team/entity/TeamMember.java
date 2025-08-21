package com.ShiXi.team.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("team_member")
public class TeamMember implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id; // 主键ID
    
    private Long teamId; // 团队ID，外键到团队表
    
    private Long userId; // 用户ID，外键到用户表
    
    private String role; // 成员角色，leader表示团队负责人，member表示普通成员
    
    private String responsibility; // 成员职责描述
    
    private String applyReason; // 申请说明
    
    private LocalDateTime joinTime; // 加入时间
    
    private LocalDateTime quitTime; // 退出时间
    
    private String status; // 申请状态，pending表示待审核，approved表示审核通过，rejected表示审核拒绝
    
    @TableLogic
    private Integer isDeleted; // 逻辑删除标志，0-未删除，1-已删除
}