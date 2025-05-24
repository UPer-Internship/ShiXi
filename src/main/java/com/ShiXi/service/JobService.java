package com.ShiXi.service;

import com.ShiXi.dto.JobFuzzyQueryDTO;
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

    /**
     * 根据id查询岗位信息
     * @param id
     * @return
     */
    Result queryById(Long id);

    /**
     * 投递简历
     * @param id 岗位id
     * @return
     */
    Result deliverResume(Long id);

    /**
     * 到数据库中模糊查询
     * @param jobFuzzyQueryDTO 查询条件
     * @return 查询结果
     */
    Result fuzzyQuery(JobFuzzyQueryDTO jobFuzzyQueryDTO);
}
