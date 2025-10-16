package com.ShiXi.position.common.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.domin.dto.PageResult;
import com.ShiXi.position.common.entity.JobBrowsingHistory;
import com.ShiXi.common.mapper.JobBrowsingHistoryMapper;
import com.ShiXi.position.common.service.JobBrowsingHistoryService;
import com.ShiXi.position.common.domin.dto.JobBrowsingHistoryDTO;
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
public class JobBrowsingHistoryServiceImpl extends ServiceImpl<JobBrowsingHistoryMapper, JobBrowsingHistory> implements JobBrowsingHistoryService {
    
    @Resource
    private JobFullTimeService jobFullTimeService;
    
    @Resource
    private JobPartTimeService jobPartTimeService;
    
    @Resource
    private JobInternshipService jobInternshipService;
    
    @Override
    public Result recordBrowsing(JobBrowsingHistoryDTO jobBrowsingHistoryDTO) {
        try {
            // 参数校验
            if (jobBrowsingHistoryDTO.getPositionId() == null || jobBrowsingHistoryDTO.getPositionId() <= 0) {
                return Result.fail("岗位ID不能为空且必须为正数");
            }
            if (!StringUtils.hasText(jobBrowsingHistoryDTO.getPositionType())) {
                return Result.fail("岗位类型不能为空");
            }
            if (!isValidPositionType(jobBrowsingHistoryDTO.getPositionType())) {
                return Result.fail("岗位类型只能是：正职、兼职、实习");
            }
            
            // 获取当前用户ID
            if (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
                return Result.fail("用户未登录或登录已过期");
            }
            Long userId = UserHolder.getUser().getId();
            
            // 验证岗位是否存在
            boolean positionExists = switch (jobBrowsingHistoryDTO.getPositionType()) {
                case "正职" -> jobFullTimeService.getById(jobBrowsingHistoryDTO.getPositionId()) != null;
                case "兼职" -> jobPartTimeService.getById(jobBrowsingHistoryDTO.getPositionId()) != null;
                case "实习" -> jobInternshipService.getById(jobBrowsingHistoryDTO.getPositionId()) != null;
                default -> false;
            };

            if (!positionExists) {
                return Result.fail("岗位不存在");
            }
            
            // 检查是否已有相同的浏览记录，如果有则更新时间
            JobBrowsingHistory existingRecord = lambdaQuery()
                    .eq(JobBrowsingHistory::getUserId, userId)
                    .eq(JobBrowsingHistory::getPositionId, jobBrowsingHistoryDTO.getPositionId())
                    .eq(JobBrowsingHistory::getPositionType, jobBrowsingHistoryDTO.getPositionType())
                    .one();
            
            if (existingRecord != null) {
                // 更新现有记录的时间
                boolean success = lambdaUpdate()
                        .eq(JobBrowsingHistory::getId, existingRecord.getId())
                        .update();
                if (success) {
                    return Result.ok("浏览记录更新成功");
                } else {
                    return Result.fail("浏览记录更新失败");
                }
            } else {
                // 创建新的浏览记录
                JobBrowsingHistory browsingHistory = new JobBrowsingHistory();
                browsingHistory.setUserId(userId);
                browsingHistory.setPositionId(jobBrowsingHistoryDTO.getPositionId());
                browsingHistory.setPositionType(jobBrowsingHistoryDTO.getPositionType());
                
                boolean success = save(browsingHistory);
                if (success) {
                    return Result.ok("浏览记录保存成功");
                } else {
                    return Result.fail("浏览记录保存失败");
                }
            }
        } catch (Exception e) {
            log.error("记录岗位浏览失败", e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }
    
    @Override
    public Result pageQueryMyBrowsingHistory(Integer page, Integer pageSize, String positionType) {
        try {
            // 获取当前用户ID
            if (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
                return Result.fail("用户未登录或登录已过期");
            }
            Long userId = UserHolder.getUser().getId();
            
            // 设置默认分页参数
            page = page == null ? 1 : page;
            pageSize = pageSize == null ? 10 : pageSize;
            
            // 构建查询条件并分页查询浏览记录
            Page<JobBrowsingHistory> historyPage;
            if (StringUtils.hasText(positionType)) {
                if (!isValidPositionType(positionType)) {
                    return Result.fail("岗位类型只能是：正职、兼职、实习");
                }
                historyPage = lambdaQuery()
                        .eq(JobBrowsingHistory::getUserId, userId)
                        .eq(JobBrowsingHistory::getPositionType, positionType)
                        .orderByDesc(JobBrowsingHistory::getUpdateTime)
                        .page(new Page<>(page, pageSize));
            } else {
                historyPage = lambdaQuery()
                        .eq(JobBrowsingHistory::getUserId, userId)
                        .orderByDesc(JobBrowsingHistory::getUpdateTime)
                        .page(new Page<>(page, pageSize));
            }
            
            // 如果没有浏览记录，直接返回空结果
            if (historyPage.getRecords().isEmpty()) {
                PageResult result = new PageResult(0L, Collections.emptyList());
                return Result.ok(result);
            }
            
            // 封装返回结果
            PageResult result = new PageResult(historyPage.getTotal(), historyPage.getRecords());
            return Result.ok(result);
        } catch (Exception e) {
            log.error("分页查询用户浏览记录失败", e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }
    
    @Override
    public Result deleteBrowsingHistory(Long id) {
        try {
            // 参数校验
            if (id == null || id <= 0) {
                return Result.fail("浏览记录ID不能为空且必须为正数");
            }
            
            // 获取当前用户ID
            if (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
                return Result.fail("用户未登录或登录已过期");
            }
            Long userId = UserHolder.getUser().getId();
            
            // 删除指定的浏览记录（只能删除自己的记录）
            boolean success = lambdaUpdate()
                    .eq(JobBrowsingHistory::getId, id)
                    .eq(JobBrowsingHistory::getUserId, userId)
                    .remove();
            
            if (success) {
                return Result.ok("删除浏览记录成功");
            } else {
                return Result.fail("删除浏览记录失败");
            }
        } catch (Exception e) {
            log.error("删除浏览记录失败", e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }
    
    @Override
    public Result clearAllBrowsingHistory() {
        try {
            // 获取当前用户ID
            if (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
                return Result.fail("用户未登录或登录已过期");
            }
            Long userId = UserHolder.getUser().getId();
            
            // 删除当前用户的所有浏览记录
            boolean success = lambdaUpdate()
                    .eq(JobBrowsingHistory::getUserId, userId)
                    .remove();
            
            if (success) {
                return Result.ok("清空浏览记录成功");
            } else {
                return Result.fail("清空浏览记录失败");
            }
        } catch (Exception e) {
            log.error("清空浏览记录失败", e);
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