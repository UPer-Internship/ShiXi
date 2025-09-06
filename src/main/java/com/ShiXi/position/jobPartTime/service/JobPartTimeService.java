package com.ShiXi.position.jobPartTime.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.position.jobPartTime.domin.dto.JobPartTimeCreateDTO;
import com.ShiXi.position.jobPartTime.domin.dto.JobPartTimeUpdateDTO;
import com.ShiXi.position.jobPartTime.entity.JobPartTime;
import com.baomidou.mybatisplus.extension.service.IService;

public interface JobPartTimeService extends IService<JobPartTime> {
    
    /**
     * 创建兼职岗位
     * @param createDTO 创建岗位的请求参数
     * @return 创建结果
     */
    Result createJobPartTime(JobPartTimeCreateDTO createDTO);
    
    /**
     * 更新兼职岗位
     * @param updateDTO 更新岗位的请求参数
     * @return 更新结果
     */
    Result updateJobPartTime(JobPartTimeUpdateDTO updateDTO);
    
    /**
     * 删除兼职岗位
     * @param id 岗位id
     * @return 删除结果
     */
    Result deleteJobPartTime(Long id);
}