package com.ShiXi.job.jobFavourite.service;

import com.ShiXi.common.domin.dto.Result;

public interface JobFavoriteService {
    Result addFavorite(Long jobId);
    Result removeFavorite(Long jobId);
    Result isFavorite(Long jobId);
    
    /**
     * 分页查询当前用户收藏的岗位
     * @param page 页码
     * @param pageSize 每页大小
     * @return 分页查询结果
     */
    Result pageQueryMyFavorites(Integer page, Integer pageSize);
}