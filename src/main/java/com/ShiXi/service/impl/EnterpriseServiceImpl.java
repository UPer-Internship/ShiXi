package com.ShiXi.service.impl;

import com.ShiXi.dto.Result;
import com.ShiXi.dto.UserDTO;
import com.ShiXi.entity.Job;
import com.ShiXi.mapper.JobMapper;
import com.ShiXi.service.EnterpriseService;
import com.ShiXi.utils.UserHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EnterpriseServiceImpl extends ServiceImpl<JobMapper, Job> implements EnterpriseService {
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
}
