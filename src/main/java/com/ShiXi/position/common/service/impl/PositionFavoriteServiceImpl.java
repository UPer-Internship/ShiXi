package com.ShiXi.position.common.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.domin.dto.PageResult;
import com.ShiXi.position.common.entity.PositionFavorite;
import com.ShiXi.common.mapper.PositionFavoriteMapper;
import com.ShiXi.position.common.service.PositionFavoriteService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.position.jobFullTime.service.JobFullTimeService;
import com.ShiXi.position.jobInternship.service.JobInternshipService;
import com.ShiXi.position.jobPartTime.service.JobPartTimeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;

@Slf4j
@Service
public class PositionFavoriteServiceImpl extends ServiceImpl<PositionFavoriteMapper, PositionFavorite> implements PositionFavoriteService {
    
    @Resource
    private JobFullTimeService jobFullTimeService;
    
    @Resource
    private JobPartTimeService jobPartTimeService;
    
    @Resource
    private JobInternshipService jobInternshipService;
    
    @Override
    public Result addFavorite(Long positionId, String type) {
        try {
            // 参数校验
            if (positionId == null || positionId <= 0) {
                return Result.fail("岗位ID不能为空且必须为正数");
            }
            if (!StringUtils.hasText(type)) {
                return Result.fail("岗位类型不能为空");
            }
            if (!isValidPositionType(type)) {
                return Result.fail("岗位类型只能是：正职、兼职、实习");
            }
            
            // 获取当前用户ID
            if (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
                return Result.fail("用户未登录或登录已过期");
            }
            Long userId = UserHolder.getUser().getId();
            
            // 检查是否已收藏
            PositionFavorite exist = lambdaQuery()
                    .eq(PositionFavorite::getUserId, userId)
                    .eq(PositionFavorite::getPositionId, positionId)
                    .eq(PositionFavorite::getType, type)
                    .one();
            if (exist != null) {
                return Result.fail("已收藏该岗位");
            }
            
            // 创建收藏记录
            PositionFavorite favorite = new PositionFavorite();
            favorite.setUserId(userId);
            favorite.setPositionId(positionId);
            favorite.setType(type);

            // 验证岗位是否存在
            boolean positionExists = switch (type) {
                case "正职" -> jobFullTimeService.getById(positionId) != null;
                case "兼职" -> jobPartTimeService.getById(positionId) != null;
                case "实习" -> jobInternshipService.getById(positionId) != null;
                default -> false;
            };

            if (!positionExists) {
                return Result.fail("岗位不存在");
            }
            boolean success = save(favorite);
            if (success) {
                return Result.ok("收藏成功");
            } else {
                return Result.fail("收藏失败");
            }
        } catch (Exception e) {
            log.error("添加岗位收藏失败", e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }
    
    @Override
    public Result removeFavorite(Long positionId, String type) {
        try {
            // 参数校验
            if (positionId == null || positionId <= 0) {
                return Result.fail("岗位ID不能为空且必须为正数");
            }
            if (!StringUtils.hasText(type)) {
                return Result.fail("岗位类型不能为空");
            }
            
            // 获取当前用户ID
            if (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
                return Result.fail("用户未登录或登录已过期");
            }
            Long userId = UserHolder.getUser().getId();
            
            // 删除收藏记录
            boolean success = lambdaUpdate()
                    .eq(PositionFavorite::getUserId, userId)
                    .eq(PositionFavorite::getPositionId, positionId)
                    .eq(PositionFavorite::getType, type)
                    .remove();
            
            if (success) {
                return Result.ok("取消收藏成功");
            } else {
                return Result.fail("取消收藏失败");
            }
        } catch (Exception e) {
            log.error("取消岗位收藏失败", e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }
    
    @Override
    public Result isFavorite(Long positionId, String type) {
        try {
            // 参数校验
            if (positionId == null || positionId <= 0) {
                return Result.fail("岗位ID不能为空且必须为正数");
            }
            if (!StringUtils.hasText(type)) {
                return Result.fail("岗位类型不能为空");
            }
            
            // 获取当前用户ID
            if (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
                return Result.fail("用户未登录或登录已过期");
            }
            Long userId = UserHolder.getUser().getId();
            
            // 查询是否存在收藏记录
            long count = lambdaQuery()
                    .eq(PositionFavorite::getUserId, userId)
                    .eq(PositionFavorite::getPositionId, positionId)
                    .eq(PositionFavorite::getType, type)
                    .count();
            boolean exists = count > 0;
            
            return Result.ok(exists);
        } catch (Exception e) {
            log.error("查询岗位收藏状态失败", e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }
    
    @Override
    public Result pageQueryMyFavorites(Integer page, Integer pageSize, String type) {
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
            Page<PositionFavorite> favoritePage;
            if (StringUtils.hasText(type)) {
                if (!isValidPositionType(type)) {
                    return Result.fail("岗位类型只能是：正职、兼职、实习");
                }
                favoritePage = lambdaQuery()
                        .eq(PositionFavorite::getUserId, userId)
                        .eq(PositionFavorite::getType, type)
                        .orderByDesc(PositionFavorite::getCreateTime)
                        .page(new Page<>(page, pageSize));
            } else {
                favoritePage = lambdaQuery()
                        .eq(PositionFavorite::getUserId, userId)
                        .orderByDesc(PositionFavorite::getCreateTime)
                        .page(new Page<>(page, pageSize));
            }
            
            // 如果没有收藏记录，直接返回空结果
            if (favoritePage.getRecords().isEmpty()) {
                PageResult result = new PageResult(0L, Collections.emptyList());
                return Result.ok(result);
            }
            
            // 封装返回结果（这里返回收藏记录列表，具体的岗位详情可以由前端根据positionId和type再次查询）
            PageResult result = new PageResult(favoritePage.getTotal(), favoritePage.getRecords());
            return Result.ok(result);
        } catch (Exception e) {
            log.error("分页查询用户收藏岗位失败", e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }
    
    /**
     * 验证岗位类型是否有效
     */
    private boolean isValidPositionType(String type) {
        return "正职".equals(type) || "兼职".equals(type) || "实习".equals(type);
    }
}