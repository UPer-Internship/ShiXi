package com.ShiXi.position.common.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.position.common.domin.vo.JobVO;
import com.ShiXi.position.common.service.PositionQueryService;
import com.ShiXi.position.jobFullTime.entity.JobFullTime;
import com.ShiXi.position.jobFullTime.service.JobFullTimeService;
import com.ShiXi.position.jobInternship.entity.JobInternship;
import com.ShiXi.position.jobInternship.service.JobInternshipService;
import com.ShiXi.position.jobPartTime.entity.JobPartTime;
import com.ShiXi.position.jobPartTime.service.JobPartTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class PositionQueryServiceImpl implements PositionQueryService {
    
    @Resource
    private JobFullTimeService jobFullTimeService;
    
    @Resource
    private JobPartTimeService jobPartTimeService;
    
    @Resource
    private JobInternshipService jobInternshipService;
    
    @Override
    public Result getJobByIdAndType(Long id, String type) {
        try {
            if (id == null || type == null || type.trim().isEmpty()) {
                return Result.fail("参数不能为空");
            }
            
            JobVO jobVO = new JobVO();
            
            switch (type) {
                case "正职" -> {
                    JobFullTime jobFullTime = jobFullTimeService.getById(id);
                    if (jobFullTime == null) {
                        return Result.fail("岗位不存在");
                    }
                    BeanUtils.copyProperties(jobFullTime, jobVO);
                }
                case "兼职" -> {
                    JobPartTime jobPartTime = jobPartTimeService.getById(id);
                    if (jobPartTime == null) {
                        return Result.fail("岗位不存在");
                    }
                    BeanUtils.copyProperties(jobPartTime, jobVO);
                }
                case "实习" -> {
                    JobInternship jobInternship = jobInternshipService.getById(id);
                    if (jobInternship == null) {
                        return Result.fail("岗位不存在");
                    }
                    BeanUtils.copyProperties(jobInternship, jobVO);
                }
                default -> {
                    return Result.fail("不支持的岗位类型");
                }
            }
            
            return Result.ok(jobVO);
        } catch (Exception e) {
            log.error("查询岗位失败，id: {}, type: {}", id, type, e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }
}