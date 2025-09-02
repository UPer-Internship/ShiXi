package com.ShiXi.position.jobInternship.controller;

import com.ShiXi.position.jobInternship.service.JobInternshipService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/job/internship")
@Api(tags = "实习岗位相关接口")
public class JobInternshipController {

    @Resource
    private JobInternshipService jobInternshipService;
    
}