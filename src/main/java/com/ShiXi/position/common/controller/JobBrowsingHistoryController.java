package com.ShiXi.position.common.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.position.common.domin.dto.JobBrowsingHistoryDTO;
import com.ShiXi.position.common.service.JobBrowsingHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 岗位浏览记录控制器
 */
@Slf4j
@RestController
@RequestMapping("/position/browsing")
@Tag(name = "岗位浏览记录相关接口")
public class JobBrowsingHistoryController {
    
    @Resource
    private JobBrowsingHistoryService jobBrowsingHistoryService;
    
    /**
     * 记录岗位浏览
     */
    @PostMapping("/record")
    @Operation(summary = "记录岗位浏览")
    public Result recordBrowsing(@RequestBody JobBrowsingHistoryDTO jobBrowsingHistoryDTO) {
        return jobBrowsingHistoryService.recordBrowsing(jobBrowsingHistoryDTO);
    }
    
    /**
     * 分页查询当前用户的浏览记录
     */
    @GetMapping("/myHistory")
    @Operation(summary = "分页查询当前用户的浏览记录")
    public Result pageQueryMyBrowsingHistory(
            @Parameter(description = "页码") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @Parameter(description = "每页大小") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @Parameter(description = "岗位类型") @RequestParam(value = "positionType", required = false) String positionType) {
        return jobBrowsingHistoryService.pageQueryMyBrowsingHistory(page, pageSize, positionType);
    }
    
    /**
     * 删除指定的浏览记录
     */
    @DeleteMapping
    @Operation(summary = "删除指定的浏览记录")
    public Result deleteBrowsingHistory(@Parameter(description = "浏览记录ID") @RequestParam Long id) {
        return jobBrowsingHistoryService.deleteBrowsingHistory(id);
    }
    
    /**
     * 清空当前用户的所有浏览记录
     */
    @DeleteMapping("/clear")
    @Operation(summary = "清空当前用户的所有浏览记录")
    public Result clearAllBrowsingHistory() {
        return jobBrowsingHistoryService.clearAllBrowsingHistory();
    }
}