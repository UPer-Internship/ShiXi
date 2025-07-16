package com.ShiXi.controller;

import com.ShiXi.dto.Result;
import com.ShiXi.service.JobFavoriteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/job/favorite")
public class JobFavoriteController {
    @Resource
    private JobFavoriteService jobFavoriteService;

    /**
     * 添加收藏
     */
    @PostMapping
    public Result addFavorite(@RequestParam("jobId") Long jobId) {
        return jobFavoriteService.addFavorite(jobId);
    }

    /**
     * 删除收藏
     */
    @DeleteMapping
    public Result removeFavorite(@RequestParam("jobId") Long jobId) {
        return jobFavoriteService.removeFavorite(jobId);
    }

    /**
     * 判断是否收藏
     */
    @GetMapping("/isFavorite")
    public Result isFavorite(@RequestParam("jobId") Long jobId) {
        return jobFavoriteService.isFavorite(jobId);
    }
} 