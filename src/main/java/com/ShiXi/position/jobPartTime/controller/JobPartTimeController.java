package com.ShiXi.position.jobPartTime.controller;

import com.ShiXi.position.jobPartTime.service.JobPartTimeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/job/parttime")
@Api(tags = "兼职岗位相关接口")
public class JobPartTimeController {

    @Resource
    private JobPartTimeService jobPartTimeService;
    
}