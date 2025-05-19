package com.ShiXi.service;

import com.ShiXi.dto.JobPageQueryDTO;
import com.ShiXi.dto.Result;
import com.ShiXi.entity.Job;
import com.baomidou.mybatisplus.extension.service.IService;

public interface JobService extends IService<Job> {
    /**
     * 分页且按条件查询岗位
     * @param jobPageQueryDTO 查询条件
     * @return 分页查询结果
     */
    Result pageQuery(JobPageQueryDTO jobPageQueryDTO);

    Result queryById(Long id);

    Result deliverResume(Long id);
}
