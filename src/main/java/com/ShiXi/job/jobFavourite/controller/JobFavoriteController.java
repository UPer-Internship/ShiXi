package com.ShiXi.job.jobFavourite.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.job.jobFavourite.service.JobFavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/job/favorite")
@Tag(name = "废弃")
public class JobFavoriteController {
    @Resource
    private JobFavoriteService jobFavoriteService;

    /**
     * 添加收藏
     */
    @PostMapping
    @Operation(summary = "添加收藏")
    public Result addFavorite(@RequestParam("jobId") Long jobId) {
        return jobFavoriteService.addFavorite(jobId);
    }

    /**
     * 删除收藏
     */
    @DeleteMapping
    @Operation(summary = "删除收藏")
    public Result removeFavorite(@RequestParam("jobId") Long jobId) {
        return jobFavoriteService.removeFavorite(jobId);
    }

    /**
     * 判断是否收藏
     */
    @GetMapping("/isFavorite")
    @Operation(summary = "判断是否收藏")
    public Result isFavorite(@RequestParam("jobId") Long jobId) {
        return jobFavoriteService.isFavorite(jobId);
    }

    /**
     * 分页查询当前用户收藏的岗位
     */
    @GetMapping("/myFavorites")
    @Operation(summary = "分页查询当前用户收藏的岗位")
    public Result pageQueryMyFavorites(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return jobFavoriteService.pageQueryMyFavorites(page, pageSize);
    }
}