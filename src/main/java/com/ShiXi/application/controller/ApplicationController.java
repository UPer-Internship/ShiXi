package com.ShiXi.application.controller;

import com.ShiXi.application.domin.dto.ApplicationDTO;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.application.service.ApplicationService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/application")
@Api(tags = "申请相关接口")
public class ApplicationController {
    @Resource
    private ApplicationService applicationService;

    @PostMapping("/sendApplication")
    public Result sendApplication(@RequestBody ApplicationDTO applicationDTO) {
        return applicationService.sendApplication(applicationDTO);
    }

    @PostMapping("/uploadResume")
    public Result uploadResume(@RequestParam("resumeFile") MultipartFile resumeFile) {
        return applicationService.uploadResume(resumeFile);
    }

    @GetMapping("/getApplications")
    public Result getApplications(@RequestParam Long jobId){
        return applicationService.getApplicationsByJobId(jobId);
    }

    @GetMapping("/getApplicationsById")
    public Result getApplicationsById(@RequestParam Long enterpriseId){
        return applicationService.getApplicationsByEnterpriseId(enterpriseId);
    }

    @PostMapping("/handleApplication")
    public Result handleApplication(@RequestParam Long applicationId,@RequestParam String status){
        return applicationService.handleApplication(applicationId,status);
    }

    @PostMapping("/deleteApplication")
    public Result deleteApplication(@RequestParam Long applicationId){
        return applicationService.deleteApplication(applicationId);
    }
}
