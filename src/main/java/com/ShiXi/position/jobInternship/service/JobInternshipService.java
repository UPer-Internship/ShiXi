package com.ShiXi.position.jobInternship.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.position.jobInternship.domin.dto.JobInternshipCreateDTO;
import com.ShiXi.position.jobInternship.domin.dto.JobInternshipUpdateDTO;
import com.ShiXi.position.jobInternship.entity.JobInternship;
import com.baomidou.mybatisplus.extension.service.IService;

public interface JobInternshipService extends IService<JobInternship> {
    
    /**
     * 创建实习岗位
     * @param createDTO 创建岗位的请求参数
     * @return 创建结果
     */
    Result createJobInternship(JobInternshipCreateDTO createDTO);
    
    /**
     * 更新实习岗位
     * @param updateDTO 更新岗位的请求参数
     * @return 更新结果
     */
    Result updateJobInternship(JobInternshipUpdateDTO updateDTO);
    
    /**
     * 删除实习岗位
     * @param id 岗位id
     * @return 删除结果
     */
    Result deleteJobInternship(Long id);
}