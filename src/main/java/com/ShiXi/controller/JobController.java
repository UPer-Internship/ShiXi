package com.ShiXi.controller;

import com.ShiXi.dto.JobPageQueryDTO;
import com.ShiXi.dto.Result;
import com.ShiXi.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/job")
public class JobController {
    @Resource
    private JobService jobService;

    /**
     * 分页且按条件查询岗位
     * @return 分页查询结果
     */
    @GetMapping("/pageQuery")
    public Result pageQuery(@RequestBody JobPageQueryDTO jobPageQueryDTO){
        return jobService.pageQuery(jobPageQueryDTO);
    }

    /**
     * 根据岗位id返回
     * @return 单个岗位信息
     */
    @GetMapping("/queryById")
    public Result queryById(@RequestParam("id") Long id){
        return jobService.queryById(id);
    }

    @PostMapping("/deliverResume")
    public Result deliverResume(@RequestParam("id")Long id){
        return jobService.deliverResume(id);
    }
}
