package com.ShiXi.position.jobFullTime.controller;

import com.ShiXi.position.jobFullTime.service.JobFullTimeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/job/fulltime")
@Api(tags = "全职岗位相关接口")
public class JobFullTimeController {

    @Resource
    private JobFullTimeService jobFullTimeService;
    
}