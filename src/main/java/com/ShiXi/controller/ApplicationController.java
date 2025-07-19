package com.ShiXi.controller;

import com.ShiXi.dto.ApplicationDTO;
import com.ShiXi.dto.Result;
import com.ShiXi.service.ApplicationService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

    @PostMapping("/handleApplication")
    public Result handleApplication(@RequestParam Long applicationId,@RequestParam String status){
        return applicationService.handleApplication(applicationId,status);
    }
}
