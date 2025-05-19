package com.ShiXi.service;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.Job;

public interface EnterpriseService {
    Result pubJob(Job job);

    Result deleteJob(Long id);

    Result queryMyPubList();

    Result updateJob(Job job,Long id);

    Result queryPubById(Long id);

    Result queryResumeList();

    Result queryResumeById(Long id);
}
