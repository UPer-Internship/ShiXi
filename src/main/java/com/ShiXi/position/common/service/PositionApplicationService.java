package com.ShiXi.position.common.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.position.common.domin.dto.ApplicationPageQueryDTO;
import com.ShiXi.position.common.domin.dto.JobApplicationDTO;
import com.ShiXi.position.common.domin.dto.PositionApplicationQueryDTO;
import com.ShiXi.position.common.entity.JobApplication;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 岗位投递业务层接口
 */
public interface PositionApplicationService extends IService<JobApplication> {
    
    /**
     * 投递岗位
     * @param jobApplicationDTO 投递信息
     * @return 投递结果
     */
    Result applyPosition(JobApplicationDTO jobApplicationDTO);
    
    /**
     * 分页查询我投递的岗位
     * @param queryDTO 查询条件
     * @return 分页查询结果
     */
    Result getMyApplications(ApplicationPageQueryDTO queryDTO);
    
    /**
     * 分页查询收到的简历
     * @param queryDTO 查询条件
     * @return 分页查询结果
     */
    Result getReceivedApplications(ApplicationPageQueryDTO queryDTO);
    
    /**
     * 根据岗位分页查询投递该岗位的简历
     * @param queryDTO 查询条件（包含岗位ID和岗位类型）
     * @return 分页查询结果
     */
    Result getApplicationsByPosition(PositionApplicationQueryDTO queryDTO);
}