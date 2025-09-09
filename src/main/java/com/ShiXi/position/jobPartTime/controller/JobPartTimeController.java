package com.ShiXi.position.jobPartTime.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.position.jobPartTime.domin.dto.JobPartTimeCreateDTO;
import com.ShiXi.position.jobPartTime.domin.dto.JobPartTimeUpdateDTO;
import com.ShiXi.position.jobPartTime.service.JobPartTimeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/position/parttime")
@Tag(name = "兼职岗位相关接口")
public class JobPartTimeController {

    @Resource
    private JobPartTimeService jobPartTimeService;
    
    @PostMapping("/create")
    @Operation(summary="创建兼职岗位")
    public Result createJobPartTime(@RequestBody JobPartTimeCreateDTO createDTO) {
        return jobPartTimeService.createJobPartTime(createDTO);
    }
    
    @PutMapping("/update")
    @Operation(summary="更新兼职岗位")
    public Result updateJobPartTime(@RequestBody JobPartTimeUpdateDTO updateDTO) {
        return jobPartTimeService.updateJobPartTime(updateDTO);
    }
    
    @DeleteMapping("/delete")
    @Operation(summary="删除兼职岗位")
    public Result deleteJobPartTime(@RequestParam Long id) {
        return jobPartTimeService.deleteJobPartTime(id);
    }
}