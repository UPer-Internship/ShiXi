package com.ShiXi.position.jobInternship.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.position.jobInternship.domin.dto.JobInternshipCreateDTO;
import com.ShiXi.position.jobInternship.domin.dto.JobInternshipUpdateDTO;
import com.ShiXi.position.jobInternship.domin.vo.JobInternshipVO;
import com.ShiXi.position.jobInternship.entity.JobInternship;
import com.ShiXi.common.mapper.JobInternshipMapper;
import com.ShiXi.position.jobInternship.service.JobInternshipService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class JobInternshipServiceImpl extends ServiceImpl<JobInternshipMapper, JobInternship> implements JobInternshipService {
    
    @Override
    public Result createJobInternship(JobInternshipCreateDTO createDTO) {
        try {
            // 参数校验
            if (createDTO == null) {
                return Result.fail("请求参数不能为空");
            }
            
            // 验证必填字段
            if (!StringUtils.hasText(createDTO.getTitle())) {
                return Result.fail("岗位标题不能为空");
            }
            if (createDTO.getTitle().length() > 100) {
                return Result.fail("岗位标题长度不能超过100个字符");
            }
            
            if (createDTO.getCompanyId() == null || createDTO.getCompanyId() <= 0) {
                return Result.fail("公司ID不能为空且必须为正数");
            }
            
            if (!StringUtils.hasText(createDTO.getMainText())) {
                return Result.fail("岗位描述不能为空");
            }
            if (createDTO.getMainText().length() > 5000) {
                return Result.fail("岗位描述长度不能超过5000个字符");
            }
            
            // 验证薪资范围
            if (createDTO.getSalaryMin() != null && createDTO.getSalaryMin() < 0) {
                return Result.fail("最低薪资不能为负数");
            }
            if (createDTO.getSalaryMax() != null && createDTO.getSalaryMax() < 0) {
                return Result.fail("最高薪资不能为负数");
            }
            if (createDTO.getSalaryMin() != null && createDTO.getSalaryMax() != null 
                && createDTO.getSalaryMin() > createDTO.getSalaryMax()) {
                return Result.fail("最低薪资不能大于最高薪资");
            }
            
            // 验证地址信息
            if (!StringUtils.hasText(createDTO.getProvince())) {
                return Result.fail("省份不能为空");
            }
            if (!StringUtils.hasText(createDTO.getCity())) {
                return Result.fail("城市不能为空");
            }
            
            // 验证工作类型
            if (!StringUtils.hasText(createDTO.getType())) {
                return Result.fail("工作类型不能为空");
            }
            if (!isValidJobType(createDTO.getType())) {
                return Result.fail("工作类型只能是：实习");
            }
            
            // 验证职位分类
            if (!StringUtils.hasText(createDTO.getCategory())) {
                return Result.fail("职位分类不能为空");
            }
            
            // 验证标签列表
            if (createDTO.getTag() != null && createDTO.getTag().size() > 10) {
                return Result.fail("标签数量不能超过10个");
            }
            
            // 获取当前用户ID作为发布者
            if (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
                return Result.fail("用户未登录或登录已过期");
            }
            Long publisherId = UserHolder.getUser().getId();
            
            // 创建实体对象
            JobInternship jobInternship = new JobInternship();
            BeanUtils.copyProperties(createDTO, jobInternship);
            jobInternship.setPublisherId(publisherId);
            jobInternship.setStatus(1); // 默认可见
            
            // 保存到数据库
            boolean success = this.save(jobInternship);
            
            if (success) {
                // 转换为VO返回
                JobInternshipVO vo = new JobInternshipVO();
                BeanUtils.copyProperties(jobInternship, vo);
                return Result.ok(vo);
            } else {
                return Result.fail("创建岗位失败");
            }
        } catch (Exception e) {
            log.error("创建实习岗位失败", e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }
    
    @Override
    public Result updateJobInternship(JobInternshipUpdateDTO updateDTO) {
        try {
            // 参数校验
            if (updateDTO == null) {
                return Result.fail("请求参数不能为空");
            }
            
            if (updateDTO.getId() == null || updateDTO.getId() <= 0) {
                return Result.fail("岗位ID不能为空且必须为正数");
            }
            
            // 验证必填字段（如果提供了值）
            if (StringUtils.hasText(updateDTO.getTitle()) && updateDTO.getTitle().length() > 100) {
                return Result.fail("岗位标题长度不能超过100个字符");
            }
            
            if (updateDTO.getCompanyId() != null && updateDTO.getCompanyId() <= 0) {
                return Result.fail("公司ID必须为正数");
            }
            
            if (StringUtils.hasText(updateDTO.getMainText()) && updateDTO.getMainText().length() > 5000) {
                return Result.fail("岗位描述长度不能超过5000个字符");
            }
            
            // 验证薪资范围
            if (updateDTO.getSalaryMin() != null && updateDTO.getSalaryMin() < 0) {
                return Result.fail("最低薪资不能为负数");
            }
            if (updateDTO.getSalaryMax() != null && updateDTO.getSalaryMax() < 0) {
                return Result.fail("最高薪资不能为负数");
            }
            if (updateDTO.getSalaryMin() != null && updateDTO.getSalaryMax() != null 
                && updateDTO.getSalaryMin() > updateDTO.getSalaryMax()) {
                return Result.fail("最低薪资不能大于最高薪资");
            }
            
            // 验证工作类型
            if (StringUtils.hasText(updateDTO.getType()) && !isValidJobType(updateDTO.getType())) {
                return Result.fail("工作类型只能是：实习");
            }
            
            // 验证状态
            if (updateDTO.getStatus() != null && updateDTO.getStatus() != 0 && updateDTO.getStatus() != 1) {
                return Result.fail("状态只能是0（不可见）或1（可见）");
            }
            
            // 验证标签列表
            if (updateDTO.getTag() != null && updateDTO.getTag().size() > 10) {
                return Result.fail("标签数量不能超过10个");
            }
            
            // 检查岗位是否存在
            JobInternship existingJob = this.getById(updateDTO.getId());
            if (existingJob == null) {
                return Result.fail("岗位不存在");
            }
            
            // 检查是否为岗位发布者
            if (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
                return Result.fail("用户未登录或登录已过期");
            }
            Long currentUserId = UserHolder.getUser().getId();
            if (existingJob.getPublisherId() == null || !existingJob.getPublisherId().equals(currentUserId)) {
                return Result.fail("无权限修改此岗位");
            }
            
            // 增量更新实体对象,只更新提供的字段
            if (StringUtils.hasText(updateDTO.getTitle())) {
                existingJob.setTitle(updateDTO.getTitle());
            }
            if (updateDTO.getCompanyId() != null) {
                existingJob.setCompanyId(updateDTO.getCompanyId());
            }
            if (updateDTO.getSalaryMin() != null) {
                existingJob.setSalaryMin(updateDTO.getSalaryMin());
            }
            if (updateDTO.getSalaryMax() != null) {
                existingJob.setSalaryMax(updateDTO.getSalaryMax());
            }
            if (StringUtils.hasText(updateDTO.getMainText())) {
                existingJob.setMainText(updateDTO.getMainText());
            }
            if (StringUtils.hasText(updateDTO.getProvince())) {
                existingJob.setProvince(updateDTO.getProvince());
            }
            if (StringUtils.hasText(updateDTO.getCity())) {
                existingJob.setCity(updateDTO.getCity());
            }
            if (StringUtils.hasText(updateDTO.getDistrict())) {
                existingJob.setDistrict(updateDTO.getDistrict());
            }
            if (StringUtils.hasText(updateDTO.getType())) {
                existingJob.setType(updateDTO.getType());
            }
            if (updateDTO.getTag() != null) {
                existingJob.setTag(updateDTO.getTag());
            }
            if (updateDTO.getStatus() != null) {
                existingJob.setStatus(updateDTO.getStatus());
            }
            if (StringUtils.hasText(updateDTO.getCategory())) {
                existingJob.setCategory(updateDTO.getCategory());
            }
            if (StringUtils.hasText(updateDTO.getFinancingProgress())) {
                existingJob.setFinancingProgress(updateDTO.getFinancingProgress());
            }
            if (StringUtils.hasText(updateDTO.getEnterpriseScale())) {
                existingJob.setEnterpriseScale(updateDTO.getEnterpriseScale());
            }
            if (StringUtils.hasText(updateDTO.getIndustry())) {
                existingJob.setIndustry(updateDTO.getIndustry());
            }
            // 添加经验要求字段更新逻辑
            if (StringUtils.hasText(updateDTO.getExperienceRequirement())) {
                existingJob.setExperienceRequirement(updateDTO.getExperienceRequirement());
            }
            // 添加学历要求字段更新逻辑
            if (StringUtils.hasText(updateDTO.getEducationRequirement())) {
                existingJob.setEducationRequirement(updateDTO.getEducationRequirement());
            }
            
            // 更新到数据库
            boolean success = this.updateById(existingJob);
            
            if (success) {
                // 转换为VO返回
                JobInternshipVO vo = new JobInternshipVO();
                BeanUtils.copyProperties(existingJob, vo);
                return Result.ok(vo);
            } else {
                return Result.fail("更新岗位失败");
            }
        } catch (Exception e) {
            log.error("更新实习岗位失败", e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }
    
    @Override
    public Result deleteJobInternship(Long id) {
        try {
            // 参数校验
            if (id == null || id <= 0) {
                return Result.fail("岗位ID不能为空且必须为正数");
            }
            
            // 检查岗位是否存在
            JobInternship existingJob = this.getById(id);
            if (existingJob == null) {
                return Result.fail("岗位不存在");
            }
            
            // 检查是否为岗位发布者
            if (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
                return Result.fail("用户未登录或登录已过期");
            }
            Long currentUserId = UserHolder.getUser().getId();
            if (existingJob.getPublisherId() == null || !existingJob.getPublisherId().equals(currentUserId)) {
                return Result.fail("无权限删除此岗位");
            }
            
            // 逻辑删除
            boolean success = this.removeById(id);
            
            if (success) {
                return Result.ok("删除成功");
            } else {
                return Result.fail("删除岗位失败");
            }
        } catch (Exception e) {
            log.error("删除实习岗位失败", e);
            return Result.fail("系统繁忙，请稍后重试");
        }
    }
    
    /**
     * 验证工作类型是否符合
     */
    private boolean isValidJobType(String type) {
        return "实习".equals(type);
    }
}