package com.ShiXi.jobFavourite.service;

import com.ShiXi.common.domin.dto.Result;

public interface JobFavoriteService {
    Result addFavorite(Long jobId);
    Result removeFavorite(Long jobId);
    Result isFavorite(Long jobId);
} 