package com.ShiXi.job.jobFavourite.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.job.jobFavourite.entity.JobFavorite;
import com.ShiXi.common.mapper.JobFavoriteMapper;
import com.ShiXi.job.jobFavourite.service.JobFavoriteService;
import com.ShiXi.common.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class JobFavoriteServiceImpl extends ServiceImpl<JobFavoriteMapper, JobFavorite> implements JobFavoriteService {
    @Override
    public Result addFavorite(Long jobId) {
        Long userId = UserHolder.getUser().getId();
        // 只查未逻辑删除的收藏
        QueryWrapper<JobFavorite> queryWrapper = new QueryWrapper<JobFavorite>()
                .eq("user_id", userId)
                .eq("job_id", jobId)
                .eq("is_deleted", 0)
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