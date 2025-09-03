package com.ShiXi.position.jobFullTime.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.position.jobFullTime.domin.dto.JobFullTimeCreateDTO;
import com.ShiXi.position.jobFullTime.domin.dto.JobFullTimeUpdateDTO;
import com.ShiXi.position.jobFullTime.entity.JobFullTime;
import com.baomidou.mybatisplus.extension.service.IService;

public interface JobFullTimeService extends IService<JobFullTime> {
    
    /**
     * 创建全职岗位
     * @param createDTO 创建岗位的请求参数
     * @return 创建结果
     */
    Result createJobFullTime(JobFullTimeCreateDTO createDTO);
    
    /**
     * 更新全职岗位
     * @param updateDTO 更新岗位的请求参数
     * @return 更新结果
     */
    Result updateJobFullTime(JobFullTimeUpdateDTO updateDTO);
    
    /**
     * 删除全职岗位
     * @param id 岗位id
     * @return 删除结果
     */
    Result deleteJobFullTime(Long id);
}