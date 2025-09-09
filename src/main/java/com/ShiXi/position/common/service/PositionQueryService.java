package com.ShiXi.position.common.service;

import com.ShiXi.common.domin.dto.Result;

public interface PositionQueryService {
    /**
     * 根据id和type查询Job,主要是给查询岗位收藏时查详情用的
     * @param id 岗位ID
     * @param type 岗位类型：正职、兼职、实习
     * @return 查询结果
     */
    Result getJobByIdAndType(Long id, String type);
    
    /**
     * 分类分页查询我发布的岗位
     * @param page 页码
     * @param pageSize 每页大小
     * @param type 岗位类型：正职、兼职、实习（必传，不传默认为正职）
     * @return 分页查询结果
     */
    Result pageQueryMyPublishedJobs(Integer page, Integer pageSize, String type);

    /**
     * 分类分页模糊搜索岗位推荐(mysql版)
     * @param keyword 搜索关键词
     * @param page 页码
     * @param pageSize 每页大小
     * @param type 岗位类型（可选）
     * @param province 省份筛选（可选）
     * @param city 城市筛选（可选）
     * @param category 职位分类筛选（可选）
     * @param salaryMin 最低薪资筛选（可选）
     * @param salaryMax 最高薪资筛选（可选）
     * @return 搜索结果
     */
    Result searchJobs(String keyword, Integer page, Integer pageSize, String type, 
                     String province, String city, String category, 
                     Double salaryMin, Double salaryMax);
}