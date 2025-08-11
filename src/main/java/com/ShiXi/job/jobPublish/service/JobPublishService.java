package com.ShiXi.job.jobPublish.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.job.jobQuery.entity.Job;

public interface JobPublishService {
    Result pubJob(Job job);

    Result deleteJob(Long id);

    Result queryMyPubList();

    Result updateJob(Job job,Long id);

    Result queryPubById(Long id);

    Result queryResumeList();

    Result queryResumeById(Long id);

    Result changeJobStatus(Long jobId, Integer status);
    
    /**
     * 查询新投递的简历（从Redis消费）
     * @return 新投递的简历列表
     */
    Result queryNewResumeList();
    
    /**
     * 查询历史全量简历（从MySQL分页查询）
     * @param page 页码
     * @param pageSize 每页大小
     * @return 分页查询结果
     */
    Result queryHistoryResumeList(Integer page, Integer pageSize);
}
