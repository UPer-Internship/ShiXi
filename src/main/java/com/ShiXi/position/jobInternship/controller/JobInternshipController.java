package com.ShiXi.position.jobInternship.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.position.jobInternship.domin.dto.JobInternshipCreateDTO;
import com.ShiXi.position.jobInternship.domin.dto.JobInternshipUpdateDTO;
import com.ShiXi.position.jobInternship.service.JobInternshipService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/job/internship")
@Tag(name = "实习岗位相关接口")
public class JobInternshipController {

    @Resource
    private JobInternshipService jobInternshipService;
    
    @PostMapping("/create")
    @Operation(summary="创建实习岗位")
    public Result createJobInternship(@RequestBody JobInternshipCreateDTO createDTO) {
        return jobInternshipService.createJobInternship(createDTO);
    }
    
    @PutMapping("/update")
    @Operation(summary="更新实习岗位")
    public Result updateJobInternship(@RequestBody JobInternshipUpdateDTO updateDTO) {
        return jobInternshipService.updateJobInternship(updateDTO);
    }
    
    @DeleteMapping("/delete")
    @Operation(summary="删除实习岗位")
    public Result deleteJobInternship(@RequestParam Long id) {
        return jobInternshipService.deleteJobInternship(id);
    }
}