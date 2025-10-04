package com.ShiXi.position.common.service.impl;

import com.ShiXi.Resume.ResumePersonal.entity.Resume;
import com.ShiXi.Resume.ResumePersonal.service.OnlineResumeService;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.JobApplicationMapper;
import com.ShiXi.common.mapper.UserMapper;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.position.common.domin.dto.ApplicationPageQueryDTO;
import com.ShiXi.position.common.domin.dto.JobApplicationDTO;
import com.ShiXi.position.common.domin.dto.PositionApplicationQueryDTO;
import com.ShiXi.position.common.domin.vo.JobApplicationVO;
import com.ShiXi.position.common.domin.vo.ReceivedApplicationVO;
import com.ShiXi.position.common.entity.JobApplication;
import com.ShiXi.position.common.service.PositionApplicationService;
import com.ShiXi.position.jobFullTime.entity.JobFullTime;
import com.ShiXi.position.jobFullTime.service.JobFullTimeService;
import com.ShiXi.position.jobInternship.entity.JobInternship;
import com.ShiXi.position.jobInternship.service.JobInternshipService;
import com.ShiXi.position.jobPartTime.entity.JobPartTime;
import com.ShiXi.position.jobPartTime.service.JobPartTimeService;
import com.ShiXi.user.common.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 岗位投递业务层实现类
 */
@Slf4j
@Service
public class PositionApplicationServiceImpl extends ServiceImpl<JobApplicationMapper, JobApplication> implements PositionApplicationService {
    
    @Resource
    private JobApplicationMapper jobApplicationMapper;
    
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private OnlineResumeService onlineResumeService;
    
    @Autowired
    private JobFullTimeService jobFullTimeService;
    
    @Autowired
    private JobPartTimeService jobPartTimeService;
    
    @Autowired
    private JobInternshipService jobInternshipService;
    
    @Override
    public Result applyPosition(JobApplicationDTO jobApplicationDTO) {
        try {
            // 获取当前用户ID
            Long applicantId = UserHolder.getUser().getId();
            
            // 检查是否已经投递过该岗位
            boolean exists = lambdaQuery()
                    .eq(JobApplication::getApplicantId, applicantId)
                    .eq(JobApplication::getPositionId, jobApplicationDTO.getPositionId())
                    .eq(JobApplication::getPositionType, jobApplicationDTO.getPositionType())
                    .exists();
            
            if (exists) {
                return Result.fail("您已经投递过该岗位");
            }
            
            // 获取岗位发布者ID
            Long publisherId = getPublisherIdByPosition(jobApplicationDTO.getPositionId(), jobApplicationDTO.getPositionType());
            if (publisherId == null) {
                return Result.fail("岗位不存在或已被删除");
            }
            
            // 创建投递记录
            JobApplication application = new JobApplication();
            application.setApplicantId(applicantId);
            application.setPublisherId(publisherId);
            application.setPositionId(jobApplicationDTO.getPositionId());
            application.setPositionType(jobApplicationDTO.getPositionType());
            application.setStatus("待处理");
            application.setIsRead(0);
            application.setMessage(jobApplicationDTO.getMessage());
            
            // 保存投递记录
            save(application);
            
            log.info("岗位投递成功，投递者ID: {}, 岗位ID: {}, 岗位类型: {}", 
                    applicantId, jobApplicationDTO.getPositionId(), jobApplicationDTO.getPositionType());
            
            return Result.ok("投递成功");
            
        } catch (Exception e) {
            log.error("岗位投递失败", e);
            return Result.fail("投递失败，请稍后重试");
        }
    }
    
    @Override
    public Result getMyApplications(ApplicationPageQueryDTO queryDTO) {
        try {
            // 获取当前用户ID
            Long applicantId = UserHolder.getUser().getId();
            
            // 构建查询条件
            LambdaQueryWrapper<JobApplication> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(JobApplication::getApplicantId, applicantId)
                    .eq(StringUtils.hasText(queryDTO.getStatus()), JobApplication::getStatus, queryDTO.getStatus())
                    .eq(StringUtils.hasText(queryDTO.getPositionType()), JobApplication::getPositionType, queryDTO.getPositionType())
                    .orderByDesc(JobApplication::getCreateTime);
            
            // 分页查询
            Page<JobApplication> page = new Page<>(queryDTO.getPage(), queryDTO.getPageSize());
            Page<JobApplication> resultPage = page(page, queryWrapper);
            
            // 转换为VO对象
            List<JobApplicationVO> voList = new ArrayList<>();
            for (JobApplication application : resultPage.getRecords()) {
                JobApplicationVO vo = new JobApplicationVO();
                vo.setId(application.getId());
                vo.setPositionId(application.getPositionId());
                vo.setPositionType(application.getPositionType());
                vo.setStatus(application.getStatus());
                vo.setCreateTime(application.getCreateTime());
                vo.setMessage(application.getMessage());
                voList.add(vo);
            }
            
            return Result.ok(voList, resultPage.getTotal());
            
        } catch (Exception e) {
            log.error("查询我的投递记录失败", e);
            return Result.fail("查询失败，请稍后重试");
        }
    }
    
    @Override
    public Result getReceivedApplications(ApplicationPageQueryDTO queryDTO) {
        try {
            // 获取当前用户ID（岗位发布者）
            Long publisherId = UserHolder.getUser().getId();
            
            // 构建查询条件
            LambdaQueryWrapper<JobApplication> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(JobApplication::getPublisherId, publisherId)
                    .eq(StringUtils.hasText(queryDTO.getStatus()), JobApplication::getStatus, queryDTO.getStatus())
                    .eq(StringUtils.hasText(queryDTO.getPositionType()), JobApplication::getPositionType, queryDTO.getPositionType())
                    .orderByDesc(JobApplication::getCreateTime);
            
            // 分页查询
            Page<JobApplication> page = new Page<>(queryDTO.getPage(), queryDTO.getPageSize());
            Page<JobApplication> resultPage = page(page, queryWrapper);
            
            // 转换为VO对象
            List<ReceivedApplicationVO> voList = new ArrayList<>();
            for (JobApplication application : resultPage.getRecords()) {
                ReceivedApplicationVO vo = buildReceivedApplicationVO(application);
                if (vo != null) {
                    voList.add(vo);
                }
            }
            
            return Result.ok(voList, resultPage.getTotal());
            
        } catch (Exception e) {
            log.error("查询收到的投递记录失败", e);
            return Result.fail("查询失败，请稍后重试");
        }
    }
    
    /**
     * 根据岗位ID和类型获取发布者ID
     */
    private Long getPublisherIdByPosition(Long positionId, String positionType) {
        try {
            switch (positionType) {
                case "正职":
                    JobFullTime fullTimeJob = jobFullTimeService.getById(positionId);
                    return fullTimeJob != null ? fullTimeJob.getPublisherId() : null;
                case "兼职":
                    JobPartTime partTimeJob = jobPartTimeService.getById(positionId);
                    return partTimeJob != null ? partTimeJob.getPublisherId() : null;
                case "实习":
                    JobInternship internshipJob = jobInternshipService.getById(positionId);
                    return internshipJob != null ? internshipJob.getPublisherId() : null;
                default:
                    return null;
            }
        } catch (Exception e) {
            log.error("获取岗位发布者ID失败，岗位ID: {}, 岗位类型: {}", positionId, positionType, e);
            return null;
        }
    }
    

    
    @Override
    public Result getApplicationsByPosition(PositionApplicationQueryDTO queryDTO) {
        // 验证岗位是否存在且属于当前用户
        Long currentUserId = UserHolder.getUser().getId();
        Long publisherId = getPublisherIdByPosition(queryDTO.getPositionId(), queryDTO.getPositionType());
        
        if (publisherId == null) {
            return Result.fail("岗位不存在");
        }
        
        if (!publisherId.equals(currentUserId)) {
            return Result.fail("无权限查看该岗位的投递简历");
        }
        
        // 构建查询条件
        LambdaQueryWrapper<JobApplication> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JobApplication::getPositionId, queryDTO.getPositionId())
                   .eq(JobApplication::getPositionType, queryDTO.getPositionType())
                   .eq(JobApplication::getIsDeleted, 0);
        
        // 添加状态筛选条件
        if (StringUtils.hasText(queryDTO.getStatus())) {
            queryWrapper.eq(JobApplication::getStatus, queryDTO.getStatus());
        }
        
        // 按投递时间倒序排列
        queryWrapper.orderByDesc(JobApplication::getCreateTime);
        
        // 分页查询
        Page<JobApplication> page = new Page<>(queryDTO.getPage(), queryDTO.getPageSize());
        Page<JobApplication> applicationPage = this.page(page, queryWrapper);
        
        // 构建返回结果
        List<ReceivedApplicationVO> voList = new ArrayList<>();
        for (JobApplication application : applicationPage.getRecords()) {
            ReceivedApplicationVO vo = buildReceivedApplicationVO(application);
            voList.add(vo);
        }
        
        // 构建分页结果
        Page<ReceivedApplicationVO> resultPage = new Page<>();
        resultPage.setRecords(voList);
        resultPage.setTotal(applicationPage.getTotal());
        resultPage.setCurrent(applicationPage.getCurrent());
        resultPage.setSize(applicationPage.getSize());
        resultPage.setPages(applicationPage.getPages());
        
        return Result.ok(resultPage);
    }
    
    /**
     * 构建ReceivedApplicationVO对象，包含完整的简历信息
     */
    private ReceivedApplicationVO buildReceivedApplicationVO(JobApplication application) {
        try {
            ReceivedApplicationVO vo = new ReceivedApplicationVO();
            
            // 设置投递记录基本信息
            vo.setId(application.getId());
            vo.setPositionId(application.getPositionId());
            vo.setPositionType(application.getPositionType());
            vo.setApplicantId(application.getApplicantId());
            vo.setStatus(application.getStatus());
            vo.setIsRead(application.getIsRead());
            vo.setCreateTime(application.getCreateTime());
            vo.setMessage(application.getMessage());
            
            // 获取投递者的完整简历信息
            Resume resume = onlineResumeService.lambdaQuery()
                    .eq(Resume::getUserId, application.getApplicantId())
                    .one();
            
            if (resume != null) {
                // 设置简历完整信息
                vo.setResumeId(resume.getId());
                vo.setName(resume.getName());
                vo.setPhone(resume.getPhone());
                vo.setGender(resume.getGender());
                vo.setBirthDate(resume.getBirthDate());
                vo.setWechat(resume.getWechat());
                vo.setAdvantages(resume.getAdvantages());
                vo.setExpectedPosition(resume.getExpectedPosition());
                vo.setEducationExperiences(resume.getEducationExperiences());
                vo.setWorkAndInternshipExperiences(resume.getWorkAndInternshipExperiences());
                vo.setProjectExperiences(resume.getProjectExperiences());
                vo.setResumeLink(resume.getResumeLink());
                vo.setResumeCreateTime(resume.getCreateTime());
                vo.setResumeUpdateTime(resume.getUpdateTime());
            } else {
                log.warn("未找到投递者的简历信息，投递者ID: {}", application.getApplicantId());
            }
            
            return vo;
            
        } catch (Exception e) {
            log.error("构建ReceivedApplicationVO失败，投递记录ID: {}", application.getId(), e);
            return null;
        }
    }
    

}