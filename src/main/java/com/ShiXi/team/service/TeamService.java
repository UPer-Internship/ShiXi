package com.ShiXi.team.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.team.domin.dto.CreateTeamDTO;
import com.ShiXi.team.domin.dto.JoinTeamDTO;
import com.ShiXi.team.domin.dto.ApproveJoinDTO;
import com.ShiXi.team.entity.Team;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TeamService extends IService<Team> {
    /**
     * 创建团队
     * @param createTeamDTO 创建团队请求
     * @return 创建结果
     */
    Result createTeam(CreateTeamDTO createTeamDTO);

    /**
     * 申请加入团队
     * @param joinTeamDTO 加入团队请求
     * @return 申请结果
     */
    Result applyJoinTeam(JoinTeamDTO joinTeamDTO);

    /**
     * 批准/拒绝加入团队申请
     * @param approveJoinDTO 审核请求
     * @return 审核结果
     */
    Result approveJoinTeam(ApproveJoinDTO approveJoinDTO);

    /**
     * 根据UUID获取团队信息
     * @param teamUuid 团队UUID
     * @return 团队信息
     */
    Result getTeamByUuid(String teamUuid);

    /**
     * 获取我创建的团队列表
     * @return 团队列表
     */
    Result getMyCreatedTeams();

    /**
     * 获取我加入的团队列表
     * @return 团队列表
     */
    Result getMyJoinedTeams();

    /**
     * 获取团队的待审核申请列表
     * @param teamId 团队ID
     * @return 申请列表
     */
    Result getPendingApplications(Long teamId);

    /**
     * 退出团队
     * @param teamId 团队ID
     * @return 退出结果
     */
    Result quitTeam(Long teamId);

    /**
     * 解散团队（仅团队创建者可操作）
     * @param teamId 团队ID
     * @return 解散结果
     */
    Result dissolveTeam(Long teamId);

    /**
     * 修改团队信息
     * @param teamId 团队ID
     * @param createTeamDTO 修改信息
     * @return 修改结果
     */
    Result updateTeamInfo(Long teamId, CreateTeamDTO createTeamDTO);

    /**
     * 移除团队成员（仅团队创建者可操作）
     * @param teamId 团队ID
     * @param userId 要移除的用户ID
     * @return 移除结果
     */
    Result removeMember(Long teamId, Long userId);
}