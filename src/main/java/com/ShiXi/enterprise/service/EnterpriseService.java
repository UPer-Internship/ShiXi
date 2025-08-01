package com.ShiXi.enterprise.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.job.entity.Job;

public interface EnterpriseService {
    Result pubJob(Job job);

    Result deleteJob(Long id);

    Result queryMyPubList();

    Result updateJob(Job job,Long id);

    Result queryPubById(Long id);

    Result queryResumeList();

    Result queryResumeById(Long id);

    Result changeJobStatus(Long jobId, Integer status);
}
