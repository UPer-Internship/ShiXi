package com.ShiXi.position.common.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.position.common.domin.dto.ApplicationPageQueryDTO;
import com.ShiXi.position.common.domin.dto.JobApplicationDTO;
import com.ShiXi.position.common.domin.dto.PositionApplicationQueryDTO;
import com.ShiXi.position.common.service.PositionApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 岗位投递控制器
 */
@Slf4j
@RestController
@RequestMapping("/position/application")
@Tag(name = "岗位投递相关接口")
public class PositionApplicationController {
    
    @Resource
    private PositionApplicationService positionApplicationService;
    
    /**
     * 投递岗位
     */
    @PostMapping("/apply")
    @Operation(summary = "投递岗位")
    public Result applyPosition(@RequestBody JobApplicationDTO jobApplicationDTO) {
        return positionApplicationService.applyPosition(jobApplicationDTO);
    }
    
    /**
     * 分页查询我投递的岗位
     */
    @GetMapping("/my")
    @Operation(summary = "分页查询我投递的岗位")
    public Result getMyApplications(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "投递状态") @RequestParam(required = false) String status,
            @Parameter(description = "岗位类型") @RequestParam(required = false) String positionType) {
        
        ApplicationPageQueryDTO queryDTO = new ApplicationPageQueryDTO();
        queryDTO.setPage(page);
        queryDTO.setPageSize(pageSize);
        queryDTO.setStatus(status);
        queryDTO.setPositionType(positionType);
        
        return positionApplicationService.getMyApplications(queryDTO);
    }
    
    /**
     * 分页查询收到的简历
     */
    @GetMapping("/received")
    @Operation(summary = "分页查询收到的简历")
    public Result getReceivedApplications(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "投递状态") @RequestParam(required = false) String status,
            @Parameter(description = "岗位类型") @RequestParam(required = false) String positionType) {
        
        ApplicationPageQueryDTO queryDTO = new ApplicationPageQueryDTO();
        queryDTO.setPage(page);
        queryDTO.setPageSize(pageSize);
        queryDTO.setStatus(status);
        queryDTO.setPositionType(positionType);
        
        return positionApplicationService.getReceivedApplications(queryDTO);
    }
    
    /**
     * 根据岗位分页查询投递该岗位的简历
     */
    @GetMapping("/position")
    @Operation(summary = "根据岗位分页查询投递该岗位的简历")
    public Result getApplicationsByPosition(
            @Parameter(description = "岗位ID") @RequestParam Long positionId,
            @Parameter(description = "岗位类型") @RequestParam String positionType,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "投递状态") @RequestParam(required = false) String status) {
        
        PositionApplicationQueryDTO queryDTO = new PositionApplicationQueryDTO();
        queryDTO.setPositionId(positionId);
        queryDTO.setPositionType(positionType);
        queryDTO.setPage(page);
        queryDTO.setPageSize(pageSize);
        queryDTO.setStatus(status);
        
        return positionApplicationService.getApplicationsByPosition(queryDTO);
    }
}