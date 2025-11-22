package com.ShiXi.Resume.resumeBrowsingHistory.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.domin.dto.PageResult;
import com.ShiXi.Resume.resumeBrowsingHistory.entity.ResumeBrowsingHistory;
import com.ShiXi.common.mapper.ResumeBrowsingHistoryMapper;
import com.ShiXi.Resume.resumeBrowsingHistory.service.ResumeBrowsingHistoryService;
import com.ShiXi.Resume.resumeBrowsingHistory.domain.dto.ResumeBrowsingHistoryDTO;
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
public class ResumeBrowsingHistoryServiceImpl extends ServiceImpl<ResumeBrowsingHistoryMapper, ResumeBrowsingHistory> implements ResumeBrowsingHistoryService {
    
    @Resource
    private OnlineResumeService onlineResumeService;
    
    @Override
    public Result recordBrowsing(ResumeBrowsingHistoryDTO resumeBrowsingHistoryDTO) {
        try {
            // 参数校验
            if (resumeBrowsingHistoryDTO.getResumeId() == null || resumeBrowsingHistoryDTO.getResumeId() <= 0) {
                return Result.fail("简历ID不能为空且必须为正数");
            }
            
            // 获取当前用户ID
            if (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
                return Result.fail("用户未登录或登录已过期");
            }
            Long userId = UserHolder.getUser().getId();
            
            // 验证简历是否存在
            Result resumeResult = onlineResumeService.getResumeByResumeId(resumeBrowsingHistoryDTO.getResumeId());
            if (resumeResult == null || resumeResult.getData() == null) {
                return Result.fail("简历不存在");
            }
            
            // 创建新的浏览记录（每次请求都新增）
            ResumeBrowsingHistory browsingHistory = new ResumeBrowsingHistory();
            browsingHistory.setUserId(userId);
            browsingHistory.setResumeId(resumeBrowsingHistoryDTO.getResumeId());
            
            boolean success = save(browsingHistory);
            if (success) {
                return Result.ok("浏览记录保存成功");
            } else {
                return Result.fail("浏览记录保存失败");
            }
        } catch (Exception e) {
            log.error("记录简历浏览失败", e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }
    
    @Override
    public Result pageQueryMyBrowsingHistory(Integer page, Integer pageSize) {
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
            Page<ResumeBrowsingHistory> historyPage = lambdaQuery()
                    .eq(ResumeBrowsingHistory::getUserId, userId)
                    .orderByDesc(ResumeBrowsingHistory::getUpdateTime)
                    .page(new Page<>(page, pageSize));
            
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
                    .eq(ResumeBrowsingHistory::getId, id)
                    .eq(ResumeBrowsingHistory::getUserId, userId)
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
                    .eq(ResumeBrowsingHistory::getUserId, userId)
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
}