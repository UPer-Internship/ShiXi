package com.ShiXi.position.jobFullTime.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.position.jobFullTime.domin.dto.JobFullTimeCreateDTO;
import com.ShiXi.position.jobFullTime.domin.dto.JobFullTimeUpdateDTO;
import com.ShiXi.position.jobFullTime.service.JobFullTimeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/job/fulltime")
@Tag(name = "全职岗位相关接口")
public class JobFullTimeController {

    @Resource
    private JobFullTimeService jobFullTimeService;
    
    @PostMapping("/create")
    @Operation(summary="创建全职岗位")
    public Result createJobFullTime(@RequestBody JobFullTimeCreateDTO createDTO) {
        return jobFullTimeService.createJobFullTime(createDTO);
    }
    
    @PutMapping("/update")
    @Operation(summary="更新全职岗位")
    public Result updateJobFullTime(@RequestBody JobFullTimeUpdateDTO updateDTO) {
        return jobFullTimeService.updateJobFullTime(updateDTO);
    }
    
    @DeleteMapping("/delete")
    @Operation(summary="删除全职岗位")
    public Result deleteJobFullTime(@RequestParam Long id) {
        return jobFullTimeService.deleteJobFullTime(id);
    }
}