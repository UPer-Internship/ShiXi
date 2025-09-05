package com.ShiXi.team.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.team.domin.dto.CreateTeamDTO;
import com.ShiXi.team.domin.dto.JoinTeamDTO;
import com.ShiXi.team.domin.dto.ApproveJoinDTO;
import com.ShiXi.team.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/team")
@Tag(name = "团队相关接口")
public class TeamController {

    @Resource
    private TeamService teamService;

    @PostMapping("/create")
    @Operation(summary = "创建团队")
    public Result createTeam(@RequestBody CreateTeamDTO createTeamDTO) {
        return teamService.createTeam(createTeamDTO);
    }

    @PostMapping("/apply")
    @Operation(summary = "申请加入团队")
    public Result applyJoinTeam(@RequestBody JoinTeamDTO joinTeamDTO) {
        return teamService.applyJoinTeam(joinTeamDTO);
    }

    @PostMapping("/approve")
    @Operation(summary = "批准/拒绝加入团队申请")
    public Result approveJoinTeam(@RequestBody ApproveJoinDTO approveJoinDTO) {
        return teamService.approveJoinTeam(approveJoinDTO);
    }

    @GetMapping("/info")
    @Operation(summary = "根据UUID获取团队信息")
    public Result getTeamByUuid(@RequestParam String teamUuid) {
        return teamService.getTeamByUuid(teamUuid);
    }

    @GetMapping("/my/created")
    @Operation(summary = "获取我创建的团队列表")
    public Result getMyCreatedTeams() {
        return teamService.getMyCreatedTeams();
    }

    @GetMapping("/my/joined")
    @Operation(summary = "获取我加入的团队列表")
    public Result getMyJoinedTeams() {
        return teamService.getMyJoinedTeams();
    }

    @GetMapping("/applications")
    @Operation(summary = "获取团队的待审核申请列表")
    public Result getPendingApplications(@RequestParam Long teamId) {
        return teamService.getPendingApplications(teamId);
    }

    @PostMapping("/quit")
    @Operation(summary = "退出团队")
    public Result quitTeam(@RequestParam Long teamId) {
        return teamService.quitTeam(teamId);
    }

    @DeleteMapping("/dissolve")
    @Operation(summary = "解散团队")
    public Result dissolveTeam(@RequestParam Long teamId) {
        return teamService.dissolveTeam(teamId);
    }

    @PutMapping("/update")
    @Operation(summary = "修改团队信息")
    public Result updateTeamInfo(@RequestParam Long teamId, @RequestBody CreateTeamDTO createTeamDTO) {
        return teamService.updateTeamInfo(teamId, createTeamDTO);
    }

    @DeleteMapping("/remove")
    @Operation(summary = "移除团队成员")
    public Result removeMember(@RequestParam Long teamId, @RequestParam Long userId) {
        return teamService.removeMember(teamId, userId);
    }
}
