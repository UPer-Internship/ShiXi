package com.ShiXi.job.jobQuery.service.impl;

import cn.hutool.json.JSONUtil;
import com.ShiXi.application.service.ApplicationService;
import com.ShiXi.job.jobQuery.domin.dto.JobFuzzyQueryDTO;
import com.ShiXi.job.jobQuery.domin.dto.JobPageQueryDTO;
import com.ShiXi.common.domin.dto.PageResult;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.job.jobQuery.entity.Job;
import com.ShiXi.common.mapper.JobMapper;
import com.ShiXi.common.mapper.ApplicationMapper;
import com.ShiXi.application.entity.Application;
import com.ShiXi.Resume.ResumePersonal.service.impl.OnlineResumeServiceImpl;
import com.ShiXi.job.jobQuery.service.JobService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.common.utils.RedisConstants;
import com.ShiXi.common.domin.vo.InboxVO;
import com.ShiXi.user.info.studentInfo.entity.StudentInfo;
import com.ShiXi.user.info.studentInfo.service.StudentInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {
    @Resource
    private JobMapper jobMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    OnlineResumeServiceImpl onlineResumeService;
    @Resource
    private ApplicationService applicationService;
    @Resource
    private StudentInfoService studentInfoService;

    /**
     * 分页模糊匹配
     * status字段：0-可申请，1-已截止
     * @param jobPageQueryDTO
     * @return
     */
    @Override
    public Result pageQuery(JobPageQueryDTO jobPageQueryDTO) {
        // 处理分页参数默认值
        Integer page = jobPageQueryDTO.getPage() == null ? 1 : jobPageQueryDTO.getPage();
        Integer pageSize = jobPageQueryDTO.getPageSize() == null ? 10 : jobPageQueryDTO.getPageSize();

        QueryWrapper<Job> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted", 0);
        if (jobPageQueryDTO.getType() != null) {
            wrapper.eq("type", jobPageQueryDTO.getType());
        }
        if (jobPageQueryDTO.getCategory() != null) {
            wrapper.like("category", jobPageQueryDTO.getCategory());
        }
        if (jobPageQueryDTO.getIndustry() != null) {
            wrapper.like("enterprise_type", jobPageQueryDTO.getIndustry());
        }
        // 最大和最小薪水查询条件之间的关系是或，即满足最小薪水大于等于，最大薪水小于等于之中的一个即可
        if (jobPageQueryDTO.getSalaryMin() != null || jobPageQueryDTO.getSalaryMax() != null) {
            wrapper.nested(w -> {
                boolean hasMin = jobPageQueryDTO.getSalaryMin() != null;
                boolean hasMax = jobPageQueryDTO.getSalaryMax() != null;
                if (hasMin) {
                    w.ge("salary_min", jobPageQueryDTO.getSalaryMin());
                }
                if (hasMax) {
                    if (hasMin) {
                        w.or();
                    }
                    w.le("salary_max", jobPageQueryDTO.getSalaryMax());
                }
            });
        }
        if (jobPageQueryDTO.getTag() != null) {
            wrapper.like("tag", jobPageQueryDTO.getTag());
        }
        if (jobPageQueryDTO.getOnboardTime() != null) {
            wrapper.like("onboard_time", jobPageQueryDTO.getOnboardTime());
        }
        if (jobPageQueryDTO.getTotalTime() != null) {
            wrapper.eq("total_time", jobPageQueryDTO.getTotalTime());
        }
        wrapper.orderByDesc("create_time");

        Page<Job> jobPage = new Page<>(page, pageSize);
        Page<Job> resultPage = this.page(jobPage, wrapper);

        List<Job> records = resultPage.getRecords();
        // 只对当前页做专业匹配度排序（不影响分页）
        String tempMajor = null;
        try {
            Long userId = UserHolder.getUser().getId();
            // 根据userId查询StudentInfo获取major
            StudentInfo studentInfo = studentInfoService.query().eq("user_id", userId).one();
            if (studentInfo != null) {
                tempMajor = studentInfo.getMajor();
            }
        } catch (Exception e) {
            tempMajor = null;
        }
        final String userMajor = tempMajor;
        if (userMajor != null && records != null && !records.isEmpty()) {
            records.sort((a, b) -> {
                int scoreA = (a.getCategory() != null && a.getCategory().contains(userMajor)) ? 10 : 0;
                int scoreB = (b.getCategory() != null && b.getCategory().contains(userMajor)) ? 10 : 0;
                if (scoreA != scoreB) return scoreB - scoreA;
                if (a.getCreateTime() != null && b.getCreateTime() != null) {
                    return b.getCreateTime().compareTo(a.getCreateTime());
                }
                return 0;
            });
        }
        PageResult pageResult = null;
        if (records != null) {
            pageResult = new PageResult(records.size(), records);
        }
        return Result.ok(pageResult);
    }

    /**
     * 根据id查询职位
     *
     * @param id id
     * @return Job信息
     */
    @Override
    public Result queryById(Long id) {
        return Result.ok(getById(id));
    }

    /**
     * 投递简历
     * @param id 岗位id
     * @return
     */
    @Override
    public Result deliverResume(Long id) {//传入的是job的id
        //获取job对象
        //在job对象中找到发布者的id
        Long HRId = getById(id).getPublisherId();
        //找出学生的id
        Long userId = UserHolder.getUser().getId();

        try {
            // 检查是否已经投递过
            QueryWrapper<Application> checkWrapper = new QueryWrapper<>();
            checkWrapper.eq("student_id", userId)
                       .eq("job_id", id);
            Application existingApplication = applicationService.getOne(checkWrapper);
            if (existingApplication != null) {
                return Result.fail("您已经投递过该岗位");
            }

            //投递到发布者的收件箱中
            String key = RedisConstants.HR_INBOX_KEY + HRId;//hr的收件箱
            InboxVO inboxVO = new InboxVO();
            inboxVO.setJobId(id);
            inboxVO.setSubmitterId(userId);
            String Json = JSONUtil.toJsonStr(inboxVO);
            stringRedisTemplate.opsForZSet().add(key, Json, System.currentTimeMillis());
            
            // 同步回写MySQL
            Application application = new Application();
            application.setStudentId(userId);
            application.setEnterpriseId(HRId);
            application.setJobId(id);
            application.setStatus("pending");
            application.setIsRead(0);
            
            applicationService.save(application);
            log.info("简历投递成功，学生ID: {}, 岗位ID: {}, 企业ID: {}", userId, id, HRId);
            
            return Result.ok("投递成功");
        } catch (Exception e) {
            log.error("投递简历失败，学生ID: {}, 岗位ID: {}", userId, id, e);
            return Result.fail("投递失败，请稍后重试");
        }
    }

    /**
     * 到数据库中模糊查询相关的职位
     * status字段：0-可申请，1-已截止
     * @param jobFuzzyQueryDTO 查询条件
     * @return 查询结果
     */
    @Override
    public Result fuzzyQuery(JobFuzzyQueryDTO jobFuzzyQueryDTO) {
        String keyWord = jobFuzzyQueryDTO.getKeyWord();
        Page<Job> jobPage = query()
                .like(keyWord != null && !keyWord.isEmpty(), "title", keyWord)
                .or().like(keyWord != null && !keyWord.isEmpty(), "category", keyWord)
                .or().like(keyWord != null && !keyWord.isEmpty(), "type", keyWord)
                .or().like(keyWord != null && !keyWord.isEmpty(), "tag", keyWord)
                .or().like(keyWord != null && !keyWord.isEmpty(), "onboard_time", keyWord)
                .or().like(keyWord != null && !keyWord.isEmpty(), "detailed_information", keyWord)
                .or().like(keyWord != null && !keyWord.isEmpty(), "work_location", keyWord)
                .or().like(keyWord != null && !keyWord.isEmpty(), "enterprise_name", keyWord)
                .or().like(keyWord != null && !keyWord.isEmpty(), "enterprise_type", keyWord)
                .or().like(keyWord != null && !keyWord.isEmpty(), "enterprise_scale", keyWord)
                .or().like(keyWord != null && !keyWord.isEmpty(), "frequency", keyWord)
                // 新增对薪资字段的模糊查询
                .or().like(keyWord != null && !keyWord.isEmpty(), "salary_min", keyWord)
                .or().like(keyWord != null && !keyWord.isEmpty(), "salary_max", keyWord)
                .page(new Page<>(jobFuzzyQueryDTO.getPage(), jobFuzzyQueryDTO.getPageSize()));
        List<Job> records = jobPage.getRecords();
        PageResult pageResult = new PageResult(jobPage.getTotal(), records);
        return Result.ok(pageResult);
    }


}
