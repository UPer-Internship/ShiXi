package com.ShiXi.position.jobFullTime.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.position.jobFullTime.domin.dto.JobFullTimeCreateDTO;
import com.ShiXi.position.jobFullTime.domin.dto.JobFullTimeUpdateDTO;
import com.ShiXi.position.jobFullTime.service.JobFullTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    
    @PostMapping("/create")
    @ApiOperation("创建全职岗位")
    public Result createJobFullTime(@RequestBody JobFullTimeCreateDTO createDTO) {
        return jobFullTimeService.createJobFullTime(createDTO);
    }
    
    @PutMapping("/update")
    @ApiOperation("更新全职岗位")
    public Result updateJobFullTime(@RequestBody JobFullTimeUpdateDTO updateDTO) {
        return jobFullTimeService.updateJobFullTime(updateDTO);
    }
    
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除全职岗位")
    public Result deleteJobFullTime(@PathVariable Long id) {
        return jobFullTimeService.deleteJobFullTime(id);
    }
}