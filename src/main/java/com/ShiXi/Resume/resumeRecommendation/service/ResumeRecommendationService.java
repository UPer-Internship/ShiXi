package com.ShiXi.Resume.resumeRecommendation.service;

import com.ShiXi.common.domin.dto.Result;

/**
 * 简历推荐服务接口
 */
public interface ResumeRecommendationService {
    
    /**
     * 根据岗位类别推荐简历（分页）
     * @param jobCategory 岗位类别
     * @param page 页码
     * @param pageSize 每页记录数
     * @return 推荐的简历列表
     */
    Result recommendResumesByCategory(String jobCategory, Integer page, Integer pageSize);
    
    /**
     * 根据岗位类别推荐所有匹配的简历
     * @param jobCategory 岗位类别
     * @return 推荐的简历列表
     */
    Result recommendAllResumesByCategory(String jobCategory);
}