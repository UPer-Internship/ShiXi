package com.ShiXi.service.impl;

import com.ShiXi.dto.JobPageQueryDTO;
import com.ShiXi.dto.PageResult;
import com.ShiXi.dto.Result;
import com.ShiXi.entity.Job;
import com.ShiXi.entity.User;
import com.ShiXi.mapper.JobMapper;
import com.ShiXi.mapper.UserMapper;
import com.ShiXi.service.JobService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {
    @Resource
    private JobMapper jobMapper;
    /**
     * 分页模糊匹配
     *
     * @param jobPageQueryDTO
     * @return
     */
    @Override
    public Result pageQuery(JobPageQueryDTO jobPageQueryDTO) {
        log.info("分页查询职位信息{}", jobPageQueryDTO);
        // 创建分页对象
        Page<Job> jobPage = new Page<>(jobPageQueryDTO.getPage(), jobPageQueryDTO.getPageSize());

        // 创建查询条件并使用 QueryWrapper
        QueryWrapper<Job> queryWrapper = new QueryWrapper<>();

        // 判断type字段是否为空，如果不为空则添加查询条件
        if (jobPageQueryDTO.getType() != null) {
            queryWrapper.eq("type", jobPageQueryDTO.getType());
        }

        // 判断category字段是否为空，如果不为空则添加模糊查询条件
        if (jobPageQueryDTO.getCategory() != null) {
            queryWrapper.like("category", jobPageQueryDTO.getCategory());
        }

        // 执行分页查询
        jobMapper.selectPage(jobPage, queryWrapper);

        // 封装并返回分页结果
        return Result.ok(new PageResult(jobPage.getTotal(), jobPage.getRecords()));
    }

    /**
     * 根据id查询职位
     * @param id id
     * @return Job信息
     */
    @Override
    public Result queryById(Long id) {
        return Result.ok(getById(id));
    }
}
