package com.ShiXi.Resume.resumeFavorite.service;

import com.ShiXi.common.domin.dto.Result;

public interface ResumeFavoriteService {
    /**
     * 添加简历收藏
     * @param resumeId 简历ID
     * @return 操作结果
     */
    Result addFavorite(Long resumeId);
    
    /**
     * 取消简历收藏
     * @param resumeId 简历ID
     * @return 操作结果
     */
    Result removeFavorite(Long resumeId);
    
    /**
     * 判断是否已收藏
     * @param resumeId 简历ID
     * @return 是否收藏
     */
    Result isFavorite(Long resumeId);
    
    /**
     * 分页查询当前用户收藏的简历
     * @param page 页码
     * @param pageSize 每页大小
     * @return 分页查询结果
     */
    Result pageQueryMyFavorites(Integer page, Integer pageSize);
}