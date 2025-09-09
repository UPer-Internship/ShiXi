package com.ShiXi.position.common.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.position.common.service.PositionFavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/position/favorite")
@Tag(name = "岗位收藏相关接口")
public class PositionFavoriteController {
    
    @Resource
    private PositionFavoriteService positionFavoriteService;
    
    /**
     * 添加收藏
     */
    @PostMapping
    @Operation(summary = "添加岗位收藏")
    public Result addFavorite(@RequestParam("positionId") Long positionId,
                             @RequestParam("type") String type) {
        return positionFavoriteService.addFavorite(positionId, type);
    }
    
    /**
     * 取消收藏
     */
    @DeleteMapping
    @Operation(summary = "取消岗位收藏")
    public Result removeFavorite(@RequestParam("positionId") Long positionId,
                                @RequestParam("type") String type) {
        return positionFavoriteService.removeFavorite(positionId, type);
    }
    
    /**
     * 判断是否收藏
     */
    @GetMapping("/isFavorite")
    @Operation(summary = "判断是否已收藏")
    public Result isFavorite(@RequestParam("positionId") Long positionId,
                            @RequestParam("type") String type) {
        return positionFavoriteService.isFavorite(positionId, type);
    }
    
    /**
     * 分页查询当前用户收藏的岗位
     */
    @GetMapping("/myFavorites")
    @Operation(summary = "分页查询当前用户收藏的岗位")
    public Result pageQueryMyFavorites(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "type", required = false) String type) {
        return positionFavoriteService.pageQueryMyFavorites(page, pageSize, type);
    }
}