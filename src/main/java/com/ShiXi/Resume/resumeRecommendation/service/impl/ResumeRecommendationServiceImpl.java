package com.ShiXi.Resume.resumeRecommendation.service.impl;

import com.ShiXi.Resume.ResumePersonal.entity.Resume;
import com.ShiXi.Resume.resumeRecommendation.service.ResumeRecommendationService;
import com.ShiXi.Resume.resumeRecommendation.domain.vo.RecommendedResumeVO;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.domin.dto.PageResult;
import com.ShiXi.common.mapper.ResumeExperienceMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 简历推荐服务实现类
 */
@Slf4j
@Service
public class ResumeRecommendationServiceImpl implements ResumeRecommendationService {
    
    @Resource
    private ResumeExperienceMapper resumeExperienceMapper;
    
    @Override
    public Result recommendResumesByCategory(String jobCategory, Integer page, Integer pageSize) {
        try {
            // 参数校验
            if (page == null || page < 1) {
                page = 1;
            }
            if (pageSize == null || pageSize < 1) {
                pageSize = 10;
            }
            if (pageSize > 100) {
                pageSize = 100; // 限制最大页面大小
            }
            
            // 构建分页查询
            Page<Resume> resumePage = new Page<>(page, pageSize);
            
            // 构建查询条件：根据expected_position字段匹配job_category
            LambdaQueryWrapper<Resume> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(Resume::getExpectedPosition, jobCategory)
                       .eq(Resume::getIsDeleted, 0) // 只查询未删除的简历
                       .orderByDesc(Resume::getUpdateTime); // 按更新时间倒序
            
            // 执行分页查询
            Page<Resume> resultPage = resumeExperienceMapper.selectPage(resumePage, queryWrapper);
            
            // 转换为VO对象
            List<RecommendedResumeVO> recommendedResumes = resultPage.getRecords().stream()
                    .map(this::convertToRecommendedResumeVO)
                    .collect(Collectors.toList());
            
            // 构建分页结果
            PageResult pageResult = new PageResult();
            pageResult.setTotal(resultPage.getTotal());
            pageResult.setRecords(recommendedResumes);
            
            log.info("根据岗位类别 [{}] 推荐简历，共找到 {} 条匹配记录", jobCategory, resultPage.getTotal());
            
            return Result.ok(pageResult);
            
        } catch (Exception e) {
            log.error("根据岗位类别推荐简历失败，jobCategory: {}, error: {}", jobCategory, e.getMessage(), e);
            return Result.fail("推荐简历失败：" + e.getMessage());
        }
    }
    
    @Override
    public Result recommendAllResumesByCategory(String jobCategory) {
        try {
            // 构建查询条件：根据expected_position字段匹配job_category
            LambdaQueryWrapper<Resume> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(Resume::getExpectedPosition, jobCategory)
                       .eq(Resume::getIsDeleted, 0) // 只查询未删除的简历
                       .orderByDesc(Resume::getUpdateTime); // 按更新时间倒序
            
            // 执行查询
            List<Resume> resumes = resumeExperienceMapper.selectList(queryWrapper);
            
            // 转换为VO对象
            List<RecommendedResumeVO> recommendedResumes = resumes.stream()
                    .map(this::convertToRecommendedResumeVO)
                    .collect(Collectors.toList());
            
            log.info("根据岗位类别 [{}] 推荐所有简历，共找到 {} 条匹配记录", jobCategory, resumes.size());
            
            return Result.ok(recommendedResumes);
            
        } catch (Exception e) {
            log.error("根据岗位类别推荐所有简历失败，jobCategory: {}, error: {}", jobCategory, e.getMessage(), e);
            return Result.fail("推荐简历失败：" + e.getMessage());
        }
    }
    
    /**
     * 将Resume实体转换为RecommendedResumeVO
     */
    private RecommendedResumeVO convertToRecommendedResumeVO(Resume resume) {
        RecommendedResumeVO vo = new RecommendedResumeVO();
        vo.setId(resume.getId());
        vo.setUserId(resume.getUserId());
        vo.setName(resume.getName());
        vo.setPhone(resume.getPhone());
        vo.setGender(resume.getGender());
        vo.setBirthDate(resume.getBirthDate());
        vo.setWechat(resume.getWechat());
        vo.setExpectedPosition(resume.getExpectedPosition());
        vo.setAdvantages(resume.getAdvantages());
        vo.setResumeLink(resume.getResumeLink());
        vo.setCreateTime(resume.getCreateTime());
        vo.setUpdateTime(resume.getUpdateTime());
        return vo;
    }
}