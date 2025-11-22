package com.ShiXi.Resume.resumeFavorite.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.domin.dto.PageResult;
import com.ShiXi.Resume.resumeFavorite.entity.ResumeFavorite;
import com.ShiXi.common.mapper.ResumeFavoriteMapper;
import com.ShiXi.Resume.resumeFavorite.service.ResumeFavoriteService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.Resume.ResumePersonal.service.OnlineResumeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

@Slf4j
@Service
public class ResumeFavoriteServiceImpl extends ServiceImpl<ResumeFavoriteMapper, ResumeFavorite> implements ResumeFavoriteService {
    
    @Resource
    private OnlineResumeService onlineResumeService;
    
    @Override
    public Result addFavorite(Long resumeId) {
        try {
            // 参数校验
            if (resumeId == null || resumeId <= 0) {
                return Result.fail("简历ID不能为空且必须为正数");
            }
            
            // 获取当前用户ID
            if (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
                return Result.fail("用户未登录或登录已过期");
            }
            Long userId = UserHolder.getUser().getId();
            
            // 检查是否已收藏
            ResumeFavorite exist = lambdaQuery()
                    .eq(ResumeFavorite::getUserId, userId)
                    .eq(ResumeFavorite::getResumeId, resumeId)
                    .one();
            if (exist != null) {
                return Result.fail("已收藏该简历");
            }
            
            // 验证简历是否存在
            Result resumeResult = onlineResumeService.getResumeByResumeId(resumeId);
            if (resumeResult == null || resumeResult.getData() == null) {
                return Result.fail("简历不存在");
            }
            
            // 创建收藏记录
            ResumeFavorite favorite = new ResumeFavorite();
            favorite.setUserId(userId);
            favorite.setResumeId(resumeId);
            
            boolean success = save(favorite);
            if (success) {
                return Result.ok("收藏成功");
            } else {
                return Result.fail("收藏失败");
            }
        } catch (Exception e) {
            log.error("添加简历收藏失败", e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }
    
    @Override
    public Result removeFavorite(Long resumeId) {
        try {
            // 参数校验
            if (resumeId == null || resumeId <= 0) {
                return Result.fail("简历ID不能为空且必须为正数");
            }
            
            // 获取当前用户ID
            if (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
                return Result.fail("用户未登录或登录已过期");
            }
            Long userId = UserHolder.getUser().getId();
            
            // 删除收藏记录
            boolean success = lambdaUpdate()
                    .eq(ResumeFavorite::getUserId, userId)
                    .eq(ResumeFavorite::getResumeId, resumeId)
                    .remove();
            
            if (success) {
                return Result.ok("取消收藏成功");
            } else {
                return Result.fail("取消收藏失败");
            }
        } catch (Exception e) {
            log.error("取消简历收藏失败", e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }
    
    @Override
    public Result isFavorite(Long resumeId) {
        try {
            // 参数校验
            if (resumeId == null || resumeId <= 0) {
                return Result.fail("简历ID不能为空且必须为正数");
            }
            
            // 获取当前用户ID
            if (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
                return Result.fail("用户未登录或登录已过期");
            }
            Long userId = UserHolder.getUser().getId();
            
            // 查询是否存在收藏记录
            long count = lambdaQuery()
                    .eq(ResumeFavorite::getUserId, userId)
                    .eq(ResumeFavorite::getResumeId, resumeId)
                    .count();
            boolean exists = count > 0;
            
            return Result.ok(exists);
        } catch (Exception e) {
            log.error("查询简历收藏状态失败", e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }
    
    @Override
    public Result pageQueryMyFavorites(Integer page, Integer pageSize) {
        try {
            // 获取当前用户ID
            if (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
                return Result.fail("用户未登录或登录已过期");
            }
            Long userId = UserHolder.getUser().getId();
            
            // 设置默认分页参数
            page = page == null ? 1 : page;
            pageSize = pageSize == null ? 10 : pageSize;
            
            // 构建查询条件并分页查询收藏记录
            Page<ResumeFavorite> favoritePage = lambdaQuery()
                    .eq(ResumeFavorite::getUserId, userId)
                    .orderByDesc(ResumeFavorite::getCreateTime)
                    .page(new Page<>(page, pageSize));
            
            // 如果没有收藏记录，直接返回空结果
            if (favoritePage.getRecords().isEmpty()) {
                PageResult result = new PageResult(0L, Collections.emptyList());
                return Result.ok(result);
            }
            
            // 封装返回结果（这里返回收藏记录列表，具体的简历详情可以由前端根据resumeId再次查询）
            PageResult result = new PageResult(favoritePage.getTotal(), favoritePage.getRecords());
            return Result.ok(result);
        } catch (Exception e) {
            log.error("分页查询用户收藏简历失败", e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }
}