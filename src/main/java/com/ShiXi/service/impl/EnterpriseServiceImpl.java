package com.ShiXi.service.impl;

import cn.hutool.json.JSONUtil;
import com.ShiXi.dto.Result;
import com.ShiXi.dto.UserDTO;
import com.ShiXi.entity.Job;
import com.ShiXi.entity.StudentInfo;
import com.ShiXi.mapper.JobMapper;
import com.ShiXi.mapper.StudentInfoMapper;
import com.ShiXi.service.EnterpriseService;
import com.ShiXi.service.OnlineResumeService;
import com.ShiXi.utils.UserHolder;
import com.ShiXi.vo.InboxVO;
import com.ShiXi.vo.OnlineResumeVO;
import com.ShiXi.vo.ReceiveResumeListVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class EnterpriseServiceImpl extends ServiceImpl<JobMapper, Job> implements EnterpriseService {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    StudentInfoMapper studentInfoMapper;
    @Resource
    OnlineResumeService onlineResumeService;

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
        List<Job> myPub = query().eq("publisher_id", userId).select("id","title").list();
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
        Long userId = UserHolder.getUser().getId();
        //查收自己的redis收件箱 获取一个信件的set集合 这些信件的内容是job的id和投递者的id
        String key = "inbox:"+userId;
        Set<ZSetOperations.TypedTuple<String>> resumeJsons = stringRedisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
        try {
            if(resumeJsons==null||resumeJsons.isEmpty()){
                return Result.ok(Collections.emptyList());
            }
        } catch (java.lang.Exception e) {
            throw new RuntimeException(e);
        }
        //反序列化回来成一个对象集合
        List<InboxVO> inboxVOS = new ArrayList<>();
        for(ZSetOperations.TypedTuple<String> tuple : resumeJsons){
            String Json = tuple.getValue();
            InboxVO inboxVO = JSONUtil.toBean(Json, InboxVO.class);
            inboxVOS.add(inboxVO);
        }

        List<ReceiveResumeListVO> receiveResumeListVOs = new ArrayList<>();

        for(InboxVO inboxVO : inboxVOS){
            Job job = getById(inboxVO.getJobId());
            StudentInfo studentInfo=studentInfoMapper.selectById(inboxVO.getSubmitterId());
            ReceiveResumeListVO receiveResumeListVO = new ReceiveResumeListVO();
            receiveResumeListVO.setJobId(job.getId());
            receiveResumeListVO.setJobName(job.getTitle());
            receiveResumeListVO.setSubmitterId(studentInfo.getId());
            receiveResumeListVO.setSubmitterName(studentInfo.getName());
            receiveResumeListVO.setGender(studentInfo.getGender());
            receiveResumeListVO.setSchoolName(studentInfo.getSchoolName());
            receiveResumeListVO.setEducationLevel(studentInfo.getEducationLevel());
            receiveResumeListVO.setGraduationDate(studentInfo.getGraduationDate());
            receiveResumeListVO.setMajor(studentInfo.getMajor());
            receiveResumeListVO.setIcon(studentInfo.getIcon());
            receiveResumeListVOs.add(receiveResumeListVO);
        }
        //return  Result.ok(inboxVOS);
        return  Result.ok(receiveResumeListVOs);
    }

    @Override
    public Result queryResumeById(Long id) {//传入投递者的简历id

        OnlineResumeVO onlineResumeVO = onlineResumeService.queryResumeById(id);
        return Result.ok(onlineResumeVO);
    }
}
