package com.ShiXi.common.mapper;

import com.ShiXi.position.common.entity.JobApplication;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 岗位投递记录Mapper接口
 */
@Mapper
public interface JobApplicationMapper extends BaseMapper<JobApplication> {
}