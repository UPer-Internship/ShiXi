package com.ShiXi.job.jobFavourite.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.domin.dto.PageResult;
import com.ShiXi.job.jobFavourite.entity.JobFavorite;
import com.ShiXi.job.jobQuery.entity.Job;
import com.ShiXi.common.mapper.JobFavoriteMapper;
import com.ShiXi.common.mapper.JobMapper;
import com.ShiXi.job.jobFavourite.service.JobFavoriteService;
import com.ShiXi.common.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JobFavoriteServiceImpl extends ServiceImpl<JobFavoriteMapper, JobFavorite> implements JobFavoriteService {
    
    @Resource
    private JobMapper jobMapper;
    @Override
    public Result pageQueryMyFavorites(Integer page, Integer pageSize) {
        Long userId = UserHolder.getUser().getId();
        
        // 设置默认分页参数
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 10 : pageSize;
        
        // 先分页查询当前用户的收藏记录
        Page<JobFavorite> favoritePage = page(
            new Page<>(page, pageSize),
            new QueryWrapper<JobFavorite>()
                .eq("user_id", userId)
                .orderByDesc("create_time")
        );
        
        // 提取收藏的岗位ID列表
        List<Long> jobIds = favoritePage.getRecords().stream()
            .map(JobFavorite::getJobId)
            .collect(Collectors.toList());
        
        // 如果没有收藏记录，直接返回空结果
        if (jobIds.isEmpty()) {
            PageResult result = new PageResult(0L, Collections.emptyList());
            return Result.ok(result);
        }
        
        // 根据岗位ID列表查询岗位详情
        List<Job> jobs = jobMapper.selectList(
            new QueryWrapper<Job>().in("id", jobIds)
        );
        
        // 按照收藏时间顺序重新排序岗位列表
        List<Job> sortedJobs = favoritePage.getRecords().stream()
            .map(favorite -> jobs.stream()
                .filter(job -> job.getId().equals(favorite.getJobId()))
                .findFirst()
                .orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        
        // 封装返回结果
        PageResult result = new PageResult(favoritePage.getTotal(), sortedJobs);
        return Result.ok(result);
    }

    @Override
    public Result addFavorite(Long jobId) {
        Long userId = UserHolder.getUser().getId();
        // 检查是否已收藏
        QueryWrapper<JobFavorite> queryWrapper = new QueryWrapper<JobFavorite>()
                .eq("user_id", userId)
                .eq("job_id", jobId)
                .last("limit 1");
        JobFavorite exist = baseMapper.selectOne(queryWrapper);
        if (exist != null) {
            return Result.fail("已收藏");
        }
        // 不存在则新增
        JobFavorite favorite = new JobFavorite();
        favorite.setUserId(userId);
        favorite.setJobId(jobId);
        save(favorite);
        return Result.ok("收藏成功");
    }

    @Override
    public Result removeFavorite(Long jobId) {
        Long userId = UserHolder.getUser().getId();
        remove(new QueryWrapper<JobFavorite>().eq("user_id", userId).eq("job_id", jobId));
        return Result.ok("取消收藏成功");
    }

    @Override
    public Result isFavorite(Long jobId) {
        Long userId = UserHolder.getUser().getId();
        boolean exists = count(new QueryWrapper<JobFavorite>().eq("user_id", userId).eq("job_id", jobId)) > 0;
        return Result.ok(exists);
    }
}