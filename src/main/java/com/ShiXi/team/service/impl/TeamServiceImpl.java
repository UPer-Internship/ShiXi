package com.ShiXi.team.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.team.domin.dto.CreateTeamDTO;
import com.ShiXi.team.domin.dto.JoinTeamDTO;
import com.ShiXi.team.domin.dto.ApproveJoinDTO;
import com.ShiXi.team.domin.vo.TeamVO;
import com.ShiXi.team.domin.vo.TeamMemberVO;
import com.ShiXi.team.entity.Team;
import com.ShiXi.team.entity.TeamMember;
import com.ShiXi.common.mapper.TeamMapper;
import com.ShiXi.team.service.TeamService;
import com.ShiXi.team.service.TeamMemberService;
import com.ShiXi.user.common.service.UserService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.common.utils.UuidGenerator;
import com.ShiXi.user.common.entity.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Resource
    private TeamMemberService teamMemberService;

    @Resource
    private UserService userService;

    @Resource
    private UuidGenerator uuidGenerator;

    @Override
    @Transactional
    public Result createTeam(CreateTeamDTO createTeamDTO) {
        try {
            // 输入参数验证
            if (createTeamDTO == null) {
                return Result.fail("团队信息不能为空");
            }
            if (createTeamDTO.getName() == null || createTeamDTO.getName().trim().isEmpty()) {
                return Result.fail("团队名称不能为空");
            }
            if (createTeamDTO.getName().length() > 50) {
                return Result.fail("团队名称长度不能超过50个字符");
            }
            if (createTeamDTO.getDescription() != null && createTeamDTO.getDescription().length() > 500) {
                return Result.fail("团队描述长度不能超过500个字符");
            }
            if (createTeamDTO.getSchool() != null && createTeamDTO.getSchool().length() > 100) {
                return Result.fail("学校名称长度不能超过100个字符");
            }
            if (createTeamDTO.getIndustry() != null && createTeamDTO.getIndustry().length() > 50) {
                return Result.fail("行业名称长度不能超过50个字符");
            }

            // 获取当前登录用户
            Long userId = UserHolder.getUser().getId();
            if (userId == null) {
                return Result.fail("用户未登录");
            }

            // 创建团队
            Team team = new Team();
            BeanUtils.copyProperties(createTeamDTO, team);
            team.setUuid(uuidGenerator.generateUuid(8, "BASE_62"));
            team.setLeaderId(userId);
            team.setCreateTime(LocalDateTime.now());
            team.setUpdateTime(LocalDateTime.now());

            // 保存团队
            save(team);

            // 创建团队成员记录（创建者自动成为团队负责人）
            TeamMember teamMember = new TeamMember();
            teamMember.setTeamId(team.getId());
            teamMember.setUserId(userId);
            teamMember.setRole("leader");
            teamMember.setResponsibility("团队负责人");
            teamMember.setApplyReason("团队创建者");
            teamMember.setJoinTime(LocalDateTime.now());
            teamMember.setStatus("approved");

            teamMemberService.save(teamMember);

            return Result.ok("团队创建成功，团队UUID: " + team.getUuid());
        } catch (Exception e) {
            log.error("创建团队失败", e);
            return Result.fail("创建团队失败：" + e.getMessage());
        }
    }

    @Override
    public Result applyJoinTeam(JoinTeamDTO joinTeamDTO) {
        try {
            // 输入参数验证
            if (joinTeamDTO == null) {
                return Result.fail("申请信息不能为空");
            }
            if (joinTeamDTO.getTeamUuid() == null || joinTeamDTO.getTeamUuid().trim().isEmpty()) {
                return Result.fail("团队UUID不能为空");
            }
            if (joinTeamDTO.getResponsibility() == null || joinTeamDTO.getResponsibility().trim().isEmpty()) {
                return Result.fail("职责描述不能为空");
            }
            if (joinTeamDTO.getResponsibility().length() > 200) {
                return Result.fail("职责描述长度不能超过200个字符");
            }
            if (joinTeamDTO.getApplyReason() == null || joinTeamDTO.getApplyReason().trim().isEmpty()) {
                return Result.fail("申请理由不能为空");
            }
            if (joinTeamDTO.getApplyReason().length() > 500) {
                return Result.fail("申请理由长度不能超过500个字符");
            }

            // 获取当前登录用户
            Long userId = UserHolder.getUser().getId();
            if (userId == null) {
                return Result.fail("用户未登录");
            }

            // 根据UUID查找团队
            Team team = lambdaQuery()
                    .eq(Team::getUuid, joinTeamDTO.getTeamUuid())
                    .one();

            if (team == null) {
                return Result.fail("团队不存在");
            }

            // 检查是否已经是团队成员或已申请
            TeamMember existingMember = teamMemberService.lambdaQuery()
                    .eq(TeamMember::getTeamId, team.getId())
                    .eq(TeamMember::getUserId, userId)
                    .in(TeamMember::getStatus, "pending", "approved")
                    .one();

            if (existingMember != null) {
                if ("approved".equals(existingMember.getStatus())) {
                    return Result.fail("您已经是该团队成员");
                } else if ("pending".equals(existingMember.getStatus())) {
                    return Result.fail("您已经申请过该团队，请等待审核");
                }
            }

            // 创建申请记录
            TeamMember teamMember = new TeamMember();
            teamMember.setTeamId(team.getId());
            teamMember.setUserId(userId);
            teamMember.setRole("member");
            teamMember.setResponsibility(joinTeamDTO.getResponsibility());
            teamMember.setApplyReason(joinTeamDTO.getApplyReason());
            teamMember.setStatus("pending");

            teamMemberService.save(teamMember);

            return Result.ok("申请提交成功，请等待团队负责人审核");
        } catch (Exception e) {
            log.error("申请加入团队失败", e);
            return Result.fail("申请加入团队失败：" + e.getMessage());
        }
    }

    @Override
    public Result approveJoinTeam(ApproveJoinDTO approveJoinDTO) {
        try {
            // 输入参数验证
            if (approveJoinDTO == null) {
                return Result.fail("审核信息不能为空");
            }
            if (approveJoinDTO.getTeamMemberId() == null) {
                return Result.fail("团队成员申请ID不能为空");
            }
            if (approveJoinDTO.getStatus() == null || approveJoinDTO.getStatus().trim().isEmpty()) {
                return Result.fail("审核状态不能为空");
            }
            // 验证状态值是否有效
            if (!"approved".equals(approveJoinDTO.getStatus()) && !"rejected".equals(approveJoinDTO.getStatus())) {
                return Result.fail("审核状态只能是approved或rejected");
            }

            // 获取当前登录用户
            Long userId = UserHolder.getUser().getId();
            if (userId == null) {
                return Result.fail("用户未登录");
            }

            // 获取申请记录
            TeamMember teamMember = teamMemberService.getById(approveJoinDTO.getTeamMemberId());
            if (teamMember == null) {
                return Result.fail("申请记录不存在");
            }

            // 获取团队信息
            Team team = getById(teamMember.getTeamId());
            if (team == null) {
                return Result.fail("团队不存在");
            }

            // 检查权限（只有团队创建者可以审核）
            if (!team.getLeaderId().equals(userId)) {
                return Result.fail("只有团队创建者可以审核申请");
            }

            // 检查申请状态
            if (!"pending".equals(teamMember.getStatus())) {
                return Result.fail("该申请已经被处理过了");
            }

            // 更新申请状态
            boolean updateResult = teamMemberService.lambdaUpdate()
                    .eq(TeamMember::getId, approveJoinDTO.getTeamMemberId())
                    .set(TeamMember::getStatus, approveJoinDTO.getStatus())
                    .set("approved".equals(approveJoinDTO.getStatus()), TeamMember::getJoinTime, LocalDateTime.now())
                    .update();

            if (!updateResult) {
                return Result.fail("更新申请状态失败");
            }

            String resultMsg = "approved".equals(approveJoinDTO.getStatus()) ? "申请已通过" : "申请已拒绝";
            return Result.ok(resultMsg);
        } catch (Exception e) {
            log.error("审核申请失败", e);
            return Result.fail("审核申请失败：" + e.getMessage());
        }
    }

    @Override
    public Result getTeamByUuid(String teamUuid) {
        try {
            // 输入参数验证
            if (teamUuid == null || teamUuid.trim().isEmpty()) {
                return Result.fail("团队UUID不能为空");
            }

            // 根据UUID查找团队
            Team team = lambdaQuery()
                    .eq(Team::getUuid, teamUuid)
                    .one();

            if (team == null) {
                return Result.fail("团队不存在");
            }

            // 构建团队VO
            TeamVO teamVO = buildTeamVO(team);
            return Result.ok(teamVO);
        } catch (Exception e) {
            log.error("获取团队信息失败", e);
            return Result.fail("获取团队信息失败：" + e.getMessage());
        }
    }

    @Override
    public Result getMyCreatedTeams() {
        try {
            // 获取当前登录用户
            Long userId = UserHolder.getUser().getId();

            // 查询我创建的团队
            List<Team> teams = lambdaQuery()
                    .eq(Team::getLeaderId, userId)
                    .orderByDesc(Team::getCreateTime)
                    .list();

            // 构建团队VO列表
            List<TeamVO> teamVOList = teams.stream()
                    .map(this::buildTeamVO)
                    .collect(Collectors.toList());

            return Result.ok(teamVOList);
        } catch (Exception e) {
            log.error("获取我创建的团队失败", e);
            return Result.fail("获取我创建的团队失败：" + e.getMessage());
        }
    }

    @Override
    public Result getMyJoinedTeams() {
        try {
            // 获取当前登录用户
            Long userId = UserHolder.getUser().getId();

            // 查询我加入的团队（状态为approved）
            List<TeamMember> teamMembers = teamMemberService.lambdaQuery()
                    .eq(TeamMember::getUserId, userId)
                    .eq(TeamMember::getStatus, "approved")
                    .orderByDesc(TeamMember::getJoinTime)
                    .list();

            // 获取团队信息
            List<TeamVO> teamVOList = teamMembers.stream()
                    .map(member -> {
                        Team team = getById(member.getTeamId());
                        return team != null ? buildTeamVO(team) : null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            return Result.ok(teamVOList);
        } catch (Exception e) {
            log.error("获取我加入的团队失败", e);
            return Result.fail("获取我加入的团队失败：" + e.getMessage());
        }
    }

    @Override
    public Result getPendingApplications(Long teamId) {
        try {
            // 获取当前登录用户
            Long userId = UserHolder.getUser().getId();

            // 获取团队信息并检查权限
            Team team = getById(teamId);
            if (team == null) {
                return Result.fail("团队不存在");
            }

            if (!team.getLeaderId().equals(userId)) {
                return Result.fail("只有团队创建者可以查看申请列表");
            }

            // 查询待审核申请
            List<TeamMember> pendingMembers = teamMemberService.lambdaQuery()
                    .eq(TeamMember::getTeamId, teamId)
                    .eq(TeamMember::getStatus, "pending")
                    .orderByAsc(TeamMember::getId)
                    .list();

            // 构建申请VO列表
            List<TeamMemberVO> memberVOList = pendingMembers.stream()
                    .map(this::buildTeamMemberVO)
                    .collect(Collectors.toList());

            return Result.ok(memberVOList);
        } catch (Exception e) {
            log.error("获取待审核申请失败", e);
            return Result.fail("获取待审核申请失败：" + e.getMessage());
        }
    }

    @Override
    public Result quitTeam(Long teamId) {
        try {
            // 获取当前登录用户
            Long userId = UserHolder.getUser().getId();

            // 获取团队信息
            Team team = getById(teamId);
            if (team == null) {
                return Result.fail("团队不存在");
            }

            // 团队创建者不能退出团队
            if (team.getLeaderId().equals(userId)) {
                return Result.fail("团队创建者不能退出团队，请先解散团队或转让团队");
            }

            // 查找成员记录
            TeamMember teamMember = teamMemberService.lambdaQuery()
                    .eq(TeamMember::getTeamId, teamId)
                    .eq(TeamMember::getUserId, userId)
                    .eq(TeamMember::getStatus, "approved")
                    .one();

            if (teamMember == null) {
                return Result.fail("您不是该团队成员");
            }

            // 更新退出时间并逻辑删除
            teamMemberService.lambdaUpdate()
                    .eq(TeamMember::getId, teamMember.getId())
                    .set(TeamMember::getQuitTime, LocalDateTime.now())
                    .update();
            
            // 使用MyBatis-Plus的逻辑删除
            teamMemberService.removeById(teamMember.getId());

            return Result.ok("退出团队成功");
        } catch (Exception e) {
            log.error("退出团队失败", e);
            return Result.fail("退出团队失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result dissolveTeam(Long teamId) {
        try {
            // 获取当前登录用户
            Long userId = UserHolder.getUser().getId();

            // 获取团队信息并检查权限
            Team team = getById(teamId);
            if (team == null) {
                return Result.fail("团队不存在");
            }

            if (!team.getLeaderId().equals(userId)) {
                return Result.fail("只有团队创建者可以解散团队");
            }

            // 逻辑删除团队
            removeById(teamId);

            // 更新退出时间
            teamMemberService.lambdaUpdate()
                    .eq(TeamMember::getTeamId, teamId)
                    .set(TeamMember::getQuitTime, LocalDateTime.now())
                    .update();
            
            // 逻辑删除所有成员
            teamMemberService.lambdaUpdate()
                    .eq(TeamMember::getTeamId, teamId)
                    .remove();

            return Result.ok("团队解散成功");
        } catch (Exception e) {
            log.error("解散团队失败", e);
            return Result.fail("解散团队失败：" + e.getMessage());
        }
    }

    @Override
    public Result updateTeamInfo(Long teamId, CreateTeamDTO createTeamDTO) {
        try {
            // 获取当前登录用户
            Long userId = UserHolder.getUser().getId();

            // 获取团队信息并检查权限
            Team team = getById(teamId);
            if (team == null) {
                return Result.fail("团队不存在");
            }

            if (!team.getLeaderId().equals(userId)) {
                return Result.fail("只有团队创建者可以修改团队信息");
            }

            // 更新团队信息
            boolean updateResult = lambdaUpdate()
                    .eq(Team::getId, teamId)
                    .set(Team::getName, createTeamDTO.getName())
                    .set(Team::getDescription, createTeamDTO.getDescription())
                    .set(Team::getSchool, createTeamDTO.getSchool())
                    .set(Team::getIndustry, createTeamDTO.getIndustry())
                    .set(Team::getUpdateTime, LocalDateTime.now())
                    .update();

            if (!updateResult) {
                return Result.fail("更新团队信息失败");
            }

            return Result.ok("团队信息更新成功");
        } catch (Exception e) {
            log.error("更新团队信息失败", e);
            return Result.fail("更新团队信息失败：" + e.getMessage());
        }
    }

    @Override
    public Result removeMember(Long teamId, Long userId) {
        try {
            // 获取当前登录用户
            Long currentUserId = UserHolder.getUser().getId();

            // 获取团队信息并检查权限
            Team team = getById(teamId);
            if (team == null) {
                return Result.fail("团队不存在");
            }

            if (!team.getLeaderId().equals(currentUserId)) {
                return Result.fail("只有团队创建者可以移除成员");
            }

            // 不能移除自己
            if (userId.equals(currentUserId)) {
                return Result.fail("不能移除自己");
            }

            // 查找成员记录
            TeamMember teamMember = teamMemberService.lambdaQuery()
                    .eq(TeamMember::getTeamId, teamId)
                    .eq(TeamMember::getUserId, userId)
                    .eq(TeamMember::getStatus, "approved")
                    .one();

            if (teamMember == null) {
                return Result.fail("该用户不是团队成员");
            }

            // 更新退出时间并逻辑删除成员记录
            teamMemberService.lambdaUpdate()
                    .eq(TeamMember::getId, teamMember.getId())
                    .set(TeamMember::getQuitTime, LocalDateTime.now())
                    .update();
            
            // 使用MyBatis-Plus的逻辑删除
            teamMemberService.removeById(teamMember.getId());

            return Result.ok("成员移除成功");
        } catch (Exception e) {
            log.error("移除成员失败", e);
            return Result.fail("移除成员失败：" + e.getMessage());
        }
    }

    /**
     * 构建团队VO对象
     */
    private TeamVO buildTeamVO(Team team) {
        TeamVO teamVO = new TeamVO();
        BeanUtils.copyProperties(team, teamVO);

        // 获取团队创建者信息
        User leader = userService.getById(team.getLeaderId());
        if (leader != null) {
            teamVO.setLeaderName(leader.getName());
        }

        // 获取团队成员数量
        Integer memberCount = teamMemberService.lambdaQuery()
                .eq(TeamMember::getTeamId, team.getId())
                .eq(TeamMember::getStatus, "approved")
                .count();
        teamVO.setMemberCount(memberCount);

        // 获取团队成员列表
        List<TeamMember> members = teamMemberService.lambdaQuery()
                .eq(TeamMember::getTeamId, team.getId())
                .eq(TeamMember::getStatus, "approved")
                .list();
        List<TeamMemberVO> memberVOList = members.stream()
                .map(this::buildTeamMemberVO)
                .collect(Collectors.toList());
        teamVO.setMembers(memberVOList);

        return teamVO;
    }

    /**
     * 构建团队成员VO对象
     */
    private TeamMemberVO buildTeamMemberVO(TeamMember teamMember) {
        TeamMemberVO memberVO = new TeamMemberVO();
        BeanUtils.copyProperties(teamMember, memberVO);

        // 获取用户信息
        User user = userService.getById(teamMember.getUserId());
        if (user != null) {
            memberVO.setUserName(user.getName());
            memberVO.setUserIcon(user.getIcon());
        }

        return memberVO;
    }
}
