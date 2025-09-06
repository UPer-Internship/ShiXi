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
}