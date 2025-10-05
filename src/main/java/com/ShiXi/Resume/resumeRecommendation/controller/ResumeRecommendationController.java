package com.ShiXi.Resume.resumeRecommendation.controller;

import com.ShiXi.Resume.resumeRecommendation.service.ResumeRecommendationService;
import com.ShiXi.common.domin.dto.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 简历推荐相关接口
 */
@Slf4j
@RestController
@RequestMapping("/resume/recommendation")
@Tag(name = "简历推荐相关接口")
public class ResumeRecommendationController {
    
    @Resource
    private ResumeRecommendationService resumeRecommendationService;
    
    /**
     * 根据岗位类别推荐简历
     * @param jobCategory 岗位类别
     * @param page 页码，默认为1
     * @param pageSize 每页记录数，默认为10
     * @return 推荐的简历列表
     */
    @GetMapping("/recommendByCategory")
    @Operation(summary = "根据岗位类别推荐简历")
    public Result recommendResumesByCategory(
            @Parameter(description = "岗位类别") @RequestParam String jobCategory,
            @Parameter(description = "页码") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @Parameter(description = "每页记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        
        if (jobCategory == null || jobCategory.trim().isEmpty()) {
            return Result.fail("岗位类别不能为空");
        }
        
        return resumeRecommendationService.recommendResumesByCategory(jobCategory.trim(), page, pageSize);
    }
    
    /**
     * 根据岗位类别推荐简历（不分页，返回所有匹配的简历）
     * @param jobCategory 岗位类别
     * @return 推荐的简历列表
     */
    @GetMapping("/recommendAllByCategory")
    @Operation(summary = "根据岗位类别推荐所有匹配的简历")
    public Result recommendAllResumesByCategory(
            @Parameter(description = "岗位类别") @RequestParam String jobCategory) {
        
        if (jobCategory == null || jobCategory.trim().isEmpty()) {
            return Result.fail("岗位类别不能为空");
        }
        
        return resumeRecommendationService.recommendAllResumesByCategory(jobCategory.trim());
    }
}