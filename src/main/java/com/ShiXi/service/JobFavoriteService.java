package com.ShiXi.service;

import com.ShiXi.dto.Result;

public interface JobFavoriteService {
    Result addFavorite(Long jobId);
    Result removeFavorite(Long jobId);
    Result isFavorite(Long jobId);
} 