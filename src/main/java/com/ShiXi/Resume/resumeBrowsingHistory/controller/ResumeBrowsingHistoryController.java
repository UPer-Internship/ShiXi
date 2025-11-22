package com.ShiXi.Resume.resumeBrowsingHistory.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.Resume.resumeBrowsingHistory.domain.dto.ResumeBrowsingHistoryDTO;
import com.ShiXi.Resume.resumeBrowsingHistory.service.ResumeBrowsingHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 简历浏览记录控制器
 */
@Slf4j
@RestController
@RequestMapping("/resume/browsing")
@Tag(name = "简历浏览记录相关接口")
public class ResumeBrowsingHistoryController {
    
    @Resource
    private ResumeBrowsingHistoryService resumeBrowsingHistoryService;
    
    /**
     * 记录简历浏览
     */
    @PostMapping("/record")
    @Operation(summary = "记录简历浏览")
    public Result recordBrowsing(@RequestBody ResumeBrowsingHistoryDTO resumeBrowsingHistoryDTO) {
        return resumeBrowsingHistoryService.recordBrowsing(resumeBrowsingHistoryDTO);
    }
    
    /**
     * 分页查询当前用户的浏览记录
     */
    @GetMapping("/myHistory")
    @Operation(summary = "分页查询当前用户的浏览记录")
    public Result pageQueryMyBrowsingHistory(
            @Parameter(description = "页码") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @Parameter(description = "每页大小") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return resumeBrowsingHistoryService.pageQueryMyBrowsingHistory(page, pageSize);
    }
    
    /**
     * 删除指定的浏览记录
     */
    @DeleteMapping
    @Operation(summary = "删除指定的浏览记录")
    public Result deleteBrowsingHistory(@Parameter(description = "浏览记录ID") @RequestParam Long id) {
        return resumeBrowsingHistoryService.deleteBrowsingHistory(id);
    }
    
    /**
     * 清空当前用户的所有浏览记录
     */
    @DeleteMapping("/clear")
    @Operation(summary = "清空当前用户的所有浏览记录")
    public Result clearAllBrowsingHistory() {
        return resumeBrowsingHistoryService.clearAllBrowsingHistory();
    }
}