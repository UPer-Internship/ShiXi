package com.ShiXi.position.common.service;

import com.ShiXi.common.domin.dto.Result;

public interface PositionCategoryService {
    /**
     * 获取岗位类别列表
     * @return 岗位类别列表
     */
    Result getJobCategoryList();
}