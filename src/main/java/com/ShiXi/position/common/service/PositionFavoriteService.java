package com.ShiXi.position.common.service;

import com.ShiXi.common.domin.dto.Result;

public interface PositionFavoriteService {
    /**
     * 添加岗位收藏
     * @param positionId 岗位ID
     * @param type 岗位类型：正职、兼职、实习
     * @return 操作结果
     */
    Result addFavorite(Long positionId, String type);
    
    /**
     * 取消岗位收藏
     * @param positionId 岗位ID
     * @param type 岗位类型
     * @return 操作结果
     */
    Result removeFavorite(Long positionId, String type);
    
    /**
     * 判断是否已收藏
     * @param positionId 岗位ID
     * @param type 岗位类型
     * @return 是否收藏
     */
    Result isFavorite(Long positionId, String type);
    
    /**
     * 分页查询当前用户收藏的岗位
     * @param page 页码
     * @param pageSize 每页大小
     * @param type 岗位类型（可选，为空则查询所有类型）
     * @return 分页查询结果
     */
    Result pageQueryMyFavorites(Integer page, Integer pageSize, String type);
}