package com.ShiXi.job.jobPublish.service.impl;

import cn.hutool.json.JSONUtil;
import com.ShiXi.common.domin.dto.PageResult;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.ApplicationMapper;
import com.ShiXi.common.mapper.UserMapper;
import com.ShiXi.application.entity.Application;
import com.ShiXi.user.common.domin.dto.UserDTO;
import com.ShiXi.job.jobQuery.entity.Job;
import com.ShiXi.user.废弃info.studentInfo.entity.StudentInfo;
import com.ShiXi.common.mapper.JobMapper;
import com.ShiXi.common.mapper.StudentInfoMapper;
import com.ShiXi.job.jobPublish.service.JobPublishService;
import com.ShiXi.Resume.ResumePersonal.service.OnlineResumeService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.common.utils.RedisConstants;
import com.ShiXi.common.domin.vo.InboxVO;
import com.ShiXi.Resume.ResumePersonal.domin.vo.ReceiveResumeListVO;
import com.ShiXi.user.common.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class JobPublishServiceImpl extends ServiceImpl<JobMapper, Job> implements JobPublishService {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    StudentInfoMapper studentInfoMapper;
    @Resource
    OnlineResumeService onlineResumeService;
    @Resource
    UserMapper userMapper;
    @Resource
    private ApplicationMapper applicationMapper;

    @Override
    public Result pubJob(Job job) {
        UserDTO user = UserHolder.getUser();
        Long userId = user.getId();
        job.setPublisherId(userId);
        save(job);
        return Result.ok();
    }

    @Override
    public Result deleteJob(Long id) {
        Long userId = UserHolder.getUser().getId();
        Job job = getById(id);
        Long publisherId=job.getPublisherId();
        if(!publisherId.equals(userId)){
            return Result.fail("无法删除");
        }
        removeById(id);
        return Result.ok();
    }

    @Override
    public Result queryMyPubList() {
        Long userId = UserHolder.getUser().getId();
        List<Job> myPub = query().eq("publisher_id", userId).select("id","title","work_location","create_time").list();
        return Result.ok(myPub);
    }

    @Override
    public Result updateJob(Job job,Long id) {
        job.setId(id);
        updateById(job);
        return Result.ok();
    }

    @Override
    public Result queryPubById(Long id) {
        Job job = query().eq("id", id).one();
        return Result.ok(job);
    }

    @Override
    public Result queryResumeList() {
        // 保持原有逻辑不变，用于向后兼容
        return queryNewResumeList();
    }

    /**
     * 查询新投递的简历（从Redis消费）
     */
    @Override
    public Result queryNewResumeList() {
        Long userId = UserHolder.getUser().getId();
        String key = RedisConstants.HR_INBOX_KEY + userId;
        Set<ZSetOperations.TypedTuple<String>> resumeJsons = stringRedisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
        
        List<ReceiveResumeListVO> receiveResumeListVOs = new ArrayList<>();
        
        try {
            if(resumeJsons != null && !resumeJsons.isEmpty()){
                // 反序列化回来成一个对象集合
                List<InboxVO> inboxVOS = new ArrayList<>();
                for(ZSetOperations.TypedTuple<String> tuple : resumeJsons){
                    String Json = tuple.getValue();
                    InboxVO inboxVO = JSONUtil.toBean(Json, InboxVO.class);
                    inboxVOS.add(inboxVO);
                }
                
                // 构造返回对象列表
                for(InboxVO inboxVO : inboxVOS){
                    ReceiveResumeListVO receiveResumeListVO = buildReceiveResumeListVO(inboxVO);
                    if (receiveResumeListVO != null) {
                        receiveResumeListVOs.add(receiveResumeListVO);
                    }
                }
                
                // 消费Redis数据（删除已读取的数据）
                stringRedisTemplate.delete(key);
                log.info("已消费Redis收件箱数据，用户ID: {}, 数据条数: {}", userId, resumeJsons.size());
            }
            
            // Redis为空时，MySQL兜底查询最近的投递记录
            if (receiveResumeListVOs.isEmpty()) {
                log.info("Redis收件箱为空，从MySQL查询兜底数据，用户ID: {}", userId);
                // 查询最近24小时内的新投递记录
                LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
                List<Application> applications = applicationMapper.selectList(new QueryWrapper<Application>()
                        .eq("enterprise_id", userId)
                        .eq("is_deleted", 0)
                        .ge("apply_time", yesterday) // 最近24小时
                        .orderByDesc("apply_time")
                        .last("LIMIT 20")); // 限制查询最近20条
                
                for (Application application : applications) {
                    InboxVO inboxVO = new InboxVO();
                    inboxVO.setJobId(application.getJobId());
                    inboxVO.setSubmitterId(application.getStudentId());
                    
                    ReceiveResumeListVO receiveResumeListVO = buildReceiveResumeListVO(inboxVO);
                    if (receiveResumeListVO != null) {
                        receiveResumeListVOs.add(receiveResumeListVO);
                    }
                }
            }
            
        } catch (Exception e) {
            log.error("查询新投递简历失败，用户ID: {}", userId, e);
            return Result.fail("查询失败，请稍后重试");
        }
        
        return Result.ok(receiveResumeListVOs);
    }

    /**
     * 查看历史全量简历（从MySQL分页查询）
     */
    @Override
    public Result queryHistoryResumeList(Integer page, Integer pageSize) {
        Long userId = UserHolder.getUser().getId();
        
        // 处理分页参数默认值
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 10 : pageSize;
        
        try {
            // 从MySQL分页查询所有历史投递记录
            Page<Application> applicationPage = new Page<>(page, pageSize);
            Page<Application> resultPage = applicationMapper.selectPage(applicationPage, 
                new QueryWrapper<Application>()
                    .eq("enterprise_id", userId)
                    .eq("is_deleted", 0)
                    .orderByDesc("apply_time"));
            
            List<ReceiveResumeListVO> receiveResumeListVOs = new ArrayList<>();
            
            for (Application application : resultPage.getRecords()) {
                InboxVO inboxVO = new InboxVO();
                inboxVO.setJobId(application.getJobId());
                inboxVO.setSubmitterId(application.getStudentId());
                
                ReceiveResumeListVO receiveResumeListVO = buildReceiveResumeListVO(inboxVO);
                if (receiveResumeListVO != null) {
                    // 添加投递时间信息
                    receiveResumeListVO.setApplyTime(application.getApplyTime());
                    receiveResumeListVO.setStatus(application.getStatus());
                    receiveResumeListVOs.add(receiveResumeListVO);
                }
            }
            
            PageResult pageResult = new PageResult(resultPage.getTotal(), receiveResumeListVOs);
            return Result.ok(pageResult);
            
        } catch (Exception e) {
            log.error("查询历史简历失败，用户ID: {}", userId, e);
            return Result.fail("查询失败，请稍后重试");
        }
    }

    /**
     * 构建ReceiveResumeListVO对象的通用方法
     */
    private ReceiveResumeListVO buildReceiveResumeListVO(InboxVO inboxVO) {
        try {
            // 获取岗位信息
            Job job = getById(inboxVO.getJobId());
            if (job == null) {
                log.warn("岗位不存在，岗位ID: {}", inboxVO.getJobId());
                return null;
            }
            
            // 获取投递的学生信息
            StudentInfo studentInfo = studentInfoMapper.selectById(inboxVO.getSubmitterId());
            if (studentInfo == null) {
                log.warn("学生信息不存在，学生ID: {}", inboxVO.getSubmitterId());
                return null;
            }
            
            // 获取投递的用户信息
            User user = userMapper.selectById(studentInfo.getUserId());
            if (user == null) {
                log.warn("用户信息不存在，用户ID: {}", studentInfo.getUserId());
                return null;
            }
            
            // 构造返回对象
            ReceiveResumeListVO receiveResumeListVO = new ReceiveResumeListVO();
            receiveResumeListVO.setJobId(job.getId());
            receiveResumeListVO.setJobName(job.getTitle());
            receiveResumeListVO.setSubmitterId(studentInfo.getUserId());
            receiveResumeListVO.setSubmitterName(user.getName());
            receiveResumeListVO.setGender(user.getGender());
            receiveResumeListVO.setSchoolName(studentInfo.getSchoolName());
            receiveResumeListVO.setEducationLevel(studentInfo.getEducationLevel());
            receiveResumeListVO.setGraduationDate(studentInfo.getGraduationDate());
            receiveResumeListVO.setMajor(studentInfo.getMajor());
            receiveResumeListVO.setIcon(user.getIcon());
            
            return receiveResumeListVO;
        } catch (Exception e) {
            log.error("构建ReceiveResumeListVO失败，岗位ID: {}, 投递者ID: {}", 
                     inboxVO.getJobId(), inboxVO.getSubmitterId(), e);
            return null;
        }
    }

    @Override
    public Result queryResumeById(Long id) {//传入投递者的简历id
        return onlineResumeService.getResumeByResumeId(id);
    }

    @Override
    public Result changeJobStatus(Long jobId, Integer status) {
        Job job = getById(jobId);
        if (job == null) {
            return Result.fail("岗位不存在");
        }
        job.setStatus(status);
        updateById(job);
        return Result.ok("岗位状态已更新");
    }
}
