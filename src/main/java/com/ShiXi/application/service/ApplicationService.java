package com.ShiXi.application.service;

import com.ShiXi.application.domin.dto.ApplicationDTO;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.application.entity.Application;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface ApplicationService extends IService<Application> {
    Result sendApplication(ApplicationDTO applicationDTO);

    Result uploadResume(MultipartFile resumeFile);

    Result getApplicationsByJobId(Long jobId);

    Result getApplicationsByEnterpriseId(Long enterpriseId);

    Result handleApplication(Long applicationId,String status);

    Result deleteApplication(Long applicationId);
}
