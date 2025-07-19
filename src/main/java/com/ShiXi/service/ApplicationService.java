package com.ShiXi.service;

import com.ShiXi.dto.ApplicationDTO;
import com.ShiXi.dto.Result;
import com.ShiXi.entity.Application;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface ApplicationService extends IService<Application> {
    Result sendApplication(ApplicationDTO applicationDTO);

    Result uploadResume(MultipartFile resumeFile);

    Result getApplicationsByJobId(Long jobId);

    Result handleApplication(Long applicationId,String status);

    Result deleteApplication(Long applicationId);
}
