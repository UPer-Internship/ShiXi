package com.ShiXi.Resume.resumeFavorite.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.Resume.resumeFavorite.service.ResumeFavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/resume/favorite")
@Tag(name = "简历收藏相关接口")
public class ResumeFavoriteController {
    
    @Resource
    private ResumeFavoriteService resumeFavoriteService;
    
    /**
     * 添加收藏
     */
    @PostMapping
    @Operation(summary = "添加简历收藏")
    public Result addFavorite(@RequestParam("resumeId") Long resumeId) {
        return resumeFavoriteService.addFavorite(resumeId);
    }
    
    /**
     * 取消收藏
     */
    @DeleteMapping
    @Operation(summary = "取消简历收藏")
    public Result removeFavorite(@RequestParam("resumeId") Long resumeId) {
        return resumeFavoriteService.removeFavorite(resumeId);
    }
    
    /**
     * 判断是否收藏
     */
    @GetMapping("/isFavorite")
    @Operation(summary = "判断是否已收藏")
    public Result isFavorite(@RequestParam("resumeId") Long resumeId) {
        return resumeFavoriteService.isFavorite(resumeId);
    }
    
    /**
     * 分页查询当前用户收藏的简历
     */
    @GetMapping("/myFavorites")
    @Operation(summary = "分页查询当前用户收藏的简历")
    public Result pageQueryMyFavorites(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return resumeFavoriteService.pageQueryMyFavorites(page, pageSize);
    }
}