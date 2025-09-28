package com.ShiXi.position.common.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.domin.dto.PageResult;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.position.common.domin.vo.JobVO;
import com.ShiXi.position.common.service.PositionQueryService;
import com.ShiXi.position.jobFullTime.entity.JobFullTime;
import com.ShiXi.position.jobFullTime.service.JobFullTimeService;
import com.ShiXi.position.jobInternship.entity.JobInternship;
import com.ShiXi.position.jobInternship.service.JobInternshipService;
import com.ShiXi.position.jobPartTime.entity.JobPartTime;
import com.ShiXi.position.jobPartTime.service.JobPartTimeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Result pageQueryMyPublishedJobs(Integer page, Integer pageSize, String type) {
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

            // type参数处理：不传或为空默认为正职
            if (!StringUtils.hasText(type)) {
                type = "正职";
            }

            // 验证type参数的有效性
            if (!"正职".equals(type) && !"兼职".equals(type) && !"实习".equals(type)) {
                return Result.fail("岗位类型只能是：正职、兼职、实习");
            }

            // 用户验证
            if (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
                return Result.fail("用户未登录或登录已过期");
            }
            Long currentUserId = UserHolder.getUser().getId();

            // 根据类型查询对应的岗位表，使用自动分页
            PageResult pageResult;
            switch (type) {
                case "正职" -> {
                    Page<JobFullTime> jobPage = new Page<>(page, pageSize);
                    Page<JobFullTime> resultPage = jobFullTimeService.lambdaQuery()
                            .eq(JobFullTime::getPublisherId, currentUserId)
                            .orderByDesc(JobFullTime::getCreateTime)
                            .page(jobPage);

                    List<JobVO> jobVOList = resultPage.getRecords().stream()
                            .map(job -> {
                                JobVO jobVO = new JobVO();
                                BeanUtils.copyProperties(job, jobVO);
                                jobVO.setType("正职");
                                return jobVO;
                            })
                            .collect(Collectors.toList());

                    pageResult = new PageResult(resultPage.getTotal(), jobVOList);
                }
                case "兼职" -> {
                    Page<JobPartTime> jobPage = new Page<>(page, pageSize);
                    Page<JobPartTime> resultPage = jobPartTimeService.lambdaQuery()
                            .eq(JobPartTime::getPublisherId, currentUserId)
                            .orderByDesc(JobPartTime::getCreateTime)
                            .page(jobPage);

                    List<JobVO> jobVOList = resultPage.getRecords().stream()
                            .map(job -> {
                                JobVO jobVO = new JobVO();
                                BeanUtils.copyProperties(job, jobVO);
                                jobVO.setType("兼职");
                                return jobVO;
                            })
                            .collect(Collectors.toList());

                    pageResult = new PageResult(resultPage.getTotal(), jobVOList);
                }
                case "实习" -> {
                    Page<JobInternship> jobPage = new Page<>(page, pageSize);
                    Page<JobInternship> resultPage = jobInternshipService.lambdaQuery()
                            .eq(JobInternship::getPublisherId, currentUserId)
                            .orderByDesc(JobInternship::getCreateTime)
                            .page(jobPage);

                    List<JobVO> jobVOList = resultPage.getRecords().stream()
                            .map(job -> {
                                JobVO jobVO = new JobVO();
                                BeanUtils.copyProperties(job, jobVO);
                                jobVO.setType("实习");
                                return jobVO;
                            })
                            .collect(Collectors.toList());

                    pageResult = new PageResult(resultPage.getTotal(), jobVOList);
                }
                default -> {
                    return Result.fail("不支持的岗位类型");
                }
            }

            return Result.ok(pageResult);

        } catch (Exception e) {
            log.error("分页查询我发布的岗位失败，page: {}, pageSize: {}, type: {}", page, pageSize, type, e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }

    @Override
    public Result searchJobs(String keyword, Integer page, Integer pageSize, String type,
                             String province, String city, String category,
                             Double salaryMin, Double salaryMax,
                             String educationRequirement, String industry, String enterpriseScale) {
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

            List<JobVO> allJobs = new ArrayList<>();
            long totalCount = 0;

            // 如果指定了类型，只搜索该类型
            if (StringUtils.hasText(type)) {
                if (!isValidPositionType(type)) {
                    return Result.fail("岗位类型只能是：正职、兼职、实习");
                }
                PageResult result = searchJobsByType(keyword, page, pageSize, type, province, city, category, salaryMin, salaryMax, educationRequirement, industry, enterpriseScale);
                return Result.ok(result);
            } else {
                // 搜索所有类型的岗位
                List<JobVO> fullTimeJobs = searchJobsByTypeInternal(keyword, "正职", province, city, category, salaryMin, salaryMax, educationRequirement, industry, enterpriseScale);
                List<JobVO> partTimeJobs = searchJobsByTypeInternal(keyword, "兼职", province, city, category, salaryMin, salaryMax, educationRequirement, industry, enterpriseScale);
                List<JobVO> internshipJobs = searchJobsByTypeInternal(keyword, "实习", province, city, category, salaryMin, salaryMax, educationRequirement, industry, enterpriseScale);

                allJobs.addAll(fullTimeJobs);
                allJobs.addAll(partTimeJobs);
                allJobs.addAll(internshipJobs);

                // 按创建时间倒序排序
                allJobs.sort((a, b) -> b.getCreateTime().compareTo(a.getCreateTime()));

                // 手动分页
                totalCount = allJobs.size();
                int startIndex = (page - 1) * pageSize;
                int endIndex = Math.min(startIndex + pageSize, allJobs.size());

                if (startIndex >= allJobs.size()) {
                    allJobs = Collections.emptyList();
                } else {
                    allJobs = allJobs.subList(startIndex, endIndex);
                }

                PageResult pageResult = new PageResult(totalCount, allJobs);
                return Result.ok(pageResult);
            }
        } catch (Exception e) {
            log.error("搜索岗位失败，keyword: {}, page: {}, pageSize: {}, type: {}", keyword, page, pageSize, type, e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }

    /**
     * 按类型搜索岗位（分页）
     */
    private PageResult searchJobsByType(String keyword, Integer page, Integer pageSize, String type,
                                        String province, String city, String category,
                                        Double salaryMin, Double salaryMax,
                                        String educationRequirement, String industry, String enterpriseScale) {
        switch (type) {
            case "正职" -> {
                Page<JobFullTime> jobPage = new Page<>(page, pageSize);
                Page<JobFullTime> resultPage = jobFullTimeService.lambdaQuery()
                        .eq(JobFullTime::getStatus, 1) // 只查询可见的岗位
                        .and(StringUtils.hasText(keyword), wrapper ->
                                wrapper.like(JobFullTime::getTitle, keyword)
                                        .or().like(JobFullTime::getMainText, keyword)
                                        .or().like(JobFullTime::getCategory, keyword))
                        .eq(StringUtils.hasText(province), JobFullTime::getProvince, province)
                        .eq(StringUtils.hasText(city), JobFullTime::getCity, city)
                        .eq(StringUtils.hasText(category), JobFullTime::getCategory, category)
                        .ge(salaryMin != null, JobFullTime::getSalaryMin, salaryMin)
                        .le(salaryMax != null, JobFullTime::getSalaryMax, salaryMax)
                        .like(StringUtils.hasText(educationRequirement), JobFullTime::getEducationRequirement, educationRequirement)
                        .like(StringUtils.hasText(industry), JobFullTime::getIndustry, industry)
                        .like(StringUtils.hasText(enterpriseScale), JobFullTime::getEnterpriseScale, enterpriseScale)
                        .orderByDesc(JobFullTime::getCreateTime)
                        .page(jobPage);

                List<JobVO> jobVOList = resultPage.getRecords().stream()
                        .map(job -> {
                            JobVO jobVO = new JobVO();
                            BeanUtils.copyProperties(job, jobVO);
                            jobVO.setType("正职");
                            return jobVO;
                        })
                        .collect(Collectors.toList());

                return new PageResult(resultPage.getTotal(), jobVOList);
            }
            case "兼职" -> {
                Page<JobPartTime> jobPage = new Page<>(page, pageSize);
                Page<JobPartTime> resultPage = jobPartTimeService.lambdaQuery()
                        .eq(JobPartTime::getStatus, 1)
                        .and(StringUtils.hasText(keyword), wrapper ->
                                wrapper.like(JobPartTime::getTitle, keyword)
                                        .or().like(JobPartTime::getMainText, keyword)
                                        .or().like(JobPartTime::getCategory, keyword))
                        .eq(StringUtils.hasText(province), JobPartTime::getProvince, province)
                        .eq(StringUtils.hasText(city), JobPartTime::getCity, city)
                        .eq(StringUtils.hasText(category), JobPartTime::getCategory, category)
                        .ge(salaryMin != null, JobPartTime::getSalaryMin, salaryMin)
                        .le(salaryMax != null, JobPartTime::getSalaryMax, salaryMax)
                        .like(StringUtils.hasText(educationRequirement), JobPartTime::getEducationRequirement, educationRequirement)
                        .like(StringUtils.hasText(industry), JobPartTime::getIndustry, industry)
                        .like(StringUtils.hasText(enterpriseScale), JobPartTime::getEnterpriseScale, enterpriseScale)
                        .orderByDesc(JobPartTime::getCreateTime)
                        .page(jobPage);

                List<JobVO> jobVOList = resultPage.getRecords().stream()
                        .map(job -> {
                            JobVO jobVO = new JobVO();
                            BeanUtils.copyProperties(job, jobVO);
                            jobVO.setType("兼职");
                            return jobVO;
                        })
                        .collect(Collectors.toList());

                return new PageResult(resultPage.getTotal(), jobVOList);
            }
            case "实习" -> {
                Page<JobInternship> jobPage = new Page<>(page, pageSize);
                Page<JobInternship> resultPage = jobInternshipService.lambdaQuery()
                        .eq(JobInternship::getStatus, 1)
                        .and(StringUtils.hasText(keyword), wrapper ->
                                wrapper.like(JobInternship::getTitle, keyword)
                                        .or().like(JobInternship::getMainText, keyword)
                                        .or().like(JobInternship::getCategory, keyword))
                        .eq(StringUtils.hasText(province), JobInternship::getProvince, province)
                        .eq(StringUtils.hasText(city), JobInternship::getCity, city)
                        .eq(StringUtils.hasText(category), JobInternship::getCategory, category)
                        .ge(salaryMin != null, JobInternship::getSalaryMin, salaryMin)
                        .le(salaryMax != null, JobInternship::getSalaryMax, salaryMax)
                        .like(StringUtils.hasText(educationRequirement), JobInternship::getEducationRequirement, educationRequirement)
                        .like(StringUtils.hasText(industry), JobInternship::getIndustry, industry)
                        .like(StringUtils.hasText(enterpriseScale), JobInternship::getEnterpriseScale, enterpriseScale)
                        .orderByDesc(JobInternship::getCreateTime)
                        .page(jobPage);

                List<JobVO> jobVOList = resultPage.getRecords().stream()
                        .map(job -> {
                            JobVO jobVO = new JobVO();
                            BeanUtils.copyProperties(job, jobVO);
                            jobVO.setType("实习");
                            return jobVO;
                        })
                        .collect(Collectors.toList());

                return new PageResult(resultPage.getTotal(), jobVOList);
            }
            default -> {
                return new PageResult(0L, Collections.emptyList());
            }
        }
    }

    /**
     * 按类型搜索岗位（不分页，用于全类型搜索）
     */
    private List<JobVO> searchJobsByTypeInternal(String keyword, String type,
                                                 String province, String city, String category,
                                                 Double salaryMin, Double salaryMax,
                                                 String educationRequirement, String industry, String enterpriseScale) {
        switch (type) {
            case "正职" -> {
                List<JobFullTime> jobs = jobFullTimeService.lambdaQuery()
                        .eq(JobFullTime::getStatus, 1)
                        .and(StringUtils.hasText(keyword), wrapper ->
                                wrapper.like(JobFullTime::getTitle, keyword)
                                        .or().like(JobFullTime::getMainText, keyword)
                                        .or().like(JobFullTime::getCategory, keyword))
                        .eq(StringUtils.hasText(province), JobFullTime::getProvince, province)
                        .eq(StringUtils.hasText(city), JobFullTime::getCity, city)
                        .eq(StringUtils.hasText(category), JobFullTime::getCategory, category)
                        .ge(salaryMin != null, JobFullTime::getSalaryMin, salaryMin)
                        .le(salaryMax != null, JobFullTime::getSalaryMax, salaryMax)
                        .like(StringUtils.hasText(educationRequirement), JobFullTime::getEducationRequirement, educationRequirement)
                        .like(StringUtils.hasText(industry), JobFullTime::getIndustry, industry)
                        .like(StringUtils.hasText(enterpriseScale), JobFullTime::getEnterpriseScale, enterpriseScale)
                        .orderByDesc(JobFullTime::getCreateTime)
                        .list();

                return jobs.stream()
                        .map(job -> {
                            JobVO jobVO = new JobVO();
                            BeanUtils.copyProperties(job, jobVO);
                            jobVO.setType("正职");
                            return jobVO;
                        })
                        .collect(Collectors.toList());
            }
            case "兼职" -> {
                List<JobPartTime> jobs = jobPartTimeService.lambdaQuery()
                        .eq(JobPartTime::getStatus, 1)
                        .and(StringUtils.hasText(keyword), wrapper ->
                                wrapper.like(JobPartTime::getTitle, keyword)
                                        .or().like(JobPartTime::getMainText, keyword)
                                        .or().like(JobPartTime::getCategory, keyword))
                        .eq(StringUtils.hasText(province), JobPartTime::getProvince, province)
                        .eq(StringUtils.hasText(city), JobPartTime::getCity, city)
                        .eq(StringUtils.hasText(category), JobPartTime::getCategory, category)
                        .ge(salaryMin != null, JobPartTime::getSalaryMin, salaryMin)
                        .le(salaryMax != null, JobPartTime::getSalaryMax, salaryMax)
                        .like(StringUtils.hasText(educationRequirement), JobPartTime::getEducationRequirement, educationRequirement)
                        .like(StringUtils.hasText(industry), JobPartTime::getIndustry, industry)
                        .like(StringUtils.hasText(enterpriseScale), JobPartTime::getEnterpriseScale, enterpriseScale)
                        .orderByDesc(JobPartTime::getCreateTime)
                        .list();

                return jobs.stream()
                        .map(job -> {
                            JobVO jobVO = new JobVO();
                            BeanUtils.copyProperties(job, jobVO);
                            jobVO.setType("兼职");
                            return jobVO;
                        })
                        .collect(Collectors.toList());
            }
            case "实习" -> {
                List<JobInternship> jobs = jobInternshipService.lambdaQuery()
                        .eq(JobInternship::getStatus, 1)
                        .and(StringUtils.hasText(keyword), wrapper ->
                                wrapper.like(JobInternship::getTitle, keyword)
                                        .or().like(JobInternship::getMainText, keyword)
                                        .or().like(JobInternship::getCategory, keyword))
                        .eq(StringUtils.hasText(province), JobInternship::getProvince, province)
                        .eq(StringUtils.hasText(city), JobInternship::getCity, city)
                        .eq(StringUtils.hasText(category), JobInternship::getCategory, category)
                        .ge(salaryMin != null, JobInternship::getSalaryMin, salaryMin)
                        .le(salaryMax != null, JobInternship::getSalaryMax, salaryMax)
                        .like(StringUtils.hasText(educationRequirement), JobInternship::getEducationRequirement, educationRequirement)
                        .like(StringUtils.hasText(industry), JobInternship::getIndustry, industry)
                        .like(StringUtils.hasText(enterpriseScale), JobInternship::getEnterpriseScale, enterpriseScale)
                        .orderByDesc(JobInternship::getCreateTime)
                        .list();

                return jobs.stream()
                        .map(job -> {
                            JobVO jobVO = new JobVO();
                            BeanUtils.copyProperties(job, jobVO);
                            jobVO.setType("实习");
                            return jobVO;
                        })
                        .collect(Collectors.toList());
            }
            default -> {
                return Collections.emptyList();
            }
        }
    }

    /**
     * 验证岗位类型是否有效
     */
    private boolean isValidPositionType(String type) {
        return "正职".equals(type) || "兼职".equals(type) || "实习".equals(type);
    }
}