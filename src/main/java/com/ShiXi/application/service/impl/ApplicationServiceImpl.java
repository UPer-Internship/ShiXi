package com.ShiXi.application.service.impl;

import com.ShiXi.application.domin.dto.ApplicationDTO;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.application.entity.Application;
import com.ShiXi.common.mapper.ApplicationMapper;
import com.ShiXi.application.service.ApplicationService;
import com.ShiXi.common.utils.OSSUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {
    @Resource
    private ApplicationMapper applicationMapper;

    @Resource
    private OSSUtil ossUtil;

    @Value("${oss.resume.dir}")
    private String resumeDir;

    // 简历存储目录
    private static final String RESUME_DIR = "resume/";

    @Override
    public Result sendApplication(ApplicationDTO applicationDTO) {
        try {

            //  获取当前登录用户 ID
            Long studentId = UserHolder.getUser().getId();
            //Long studentId = 1L;

            //  构建申请记录
            Application application = new Application();
            application.setStudentId(studentId);
            application.setEnterpriseId(applicationDTO.getEnterpriseId());
            application.setJobId(applicationDTO.getJobId());
            application.setMessage(applicationDTO.getMessage());
            application.setResumeUrl(applicationDTO.getResumeUrl());
            application.setStatus("pending");
            application.setIsRead(0);
            application.setIsDeleted(0);
            application.setApplyTime(LocalDateTime.now());

            //  存入数据库
            applicationMapper.insert(application);

            //  返回成功结果
            return Result.ok("申请提交成功");

        } catch (Exception e) {
            log.error("申请提交失败", e);
            return Result.fail("申请提交失败：" + e.getMessage());
        }
    }

    @Override
    public Result uploadResume(MultipartFile resumeFile) {
        try {
            String resumeUrl = ossUtil.uploadAvatar(resumeFile, RESUME_DIR);
            return Result.ok(resumeUrl);
        } catch (Exception e) {
            log.error("上传简历失败", e);
            return Result.fail("上传简历失败：" + e.getMessage());
        }
    }

    @Override
    public Result getApplicationsByJobId(Long jobId) {
        try {
            List<Application> applications = applicationMapper.selectList(new QueryWrapper<Application>()
                    .eq("job_id", jobId)
                    .eq("is_deleted", 0));
            return Result.ok(applications);
        } catch (Exception e) {
            log.error("查询岗位申请失败，jobId: {}", jobId, e);
            return Result.fail("查询岗位申请失败");
        }
    }

    @Override
    public Result handleApplication(Long applicationId, String status){
        try {
            // 查询申请是否存在
            Application application = applicationMapper.selectById(applicationId);
            if (application == null) {
                return Result.fail("申请不存在");
            }

            // 更新状态
            application.setStatus(status);
            application.setUpdateTime(LocalDateTime.now());
            applicationMapper.updateById(application);

            // 标记申请已读
            markApplicationAsRead(applicationId);

            return Result.ok("申请状态更新成功");

        } catch (Exception e) {
            log.error("处理申请失败，applicationId: {}, status: {}", applicationId, status, e);
            return Result.fail("处理申请失败");
        }
    }

    @Override
    public Result deleteApplication(Long applicationId){
        boolean result = update(null,new UpdateWrapper<Application>()
                .eq("id", applicationId)
                .set("is_deleted", true));
        if( result){
            return Result.ok("删除申请成功");
        }
        else{
            return Result.fail("删除失败");
        }
    }

    private void markApplicationAsRead(Long applicationId){
        update(null,new UpdateWrapper<Application>()
                .eq("id", applicationId)
                .set("is_read", true));
    }
}
