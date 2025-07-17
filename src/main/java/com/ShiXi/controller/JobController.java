package com.ShiXi.controller;

import com.ShiXi.dto.JobFuzzyQueryDTO;
import com.ShiXi.dto.JobPageQueryDTO;
import com.ShiXi.dto.Result;
import com.ShiXi.service.JobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/job")
@Api(tags = "岗位相关接口")
public class JobController {
    @Resource
    private JobService jobService;

    /**
     * 分页且按条件查询岗位
     * @return 分页查询结果
     */
    @GetMapping("/pageQuery")
    @ApiOperation("分页且按条件查询岗位")
    public Result pageQuery( @RequestParam(required = false) Integer page,
                             @RequestParam(required = false) Integer pageSize,
                             @RequestParam(required = false) String type,
                             @RequestParam(required = false) String category){
        JobPageQueryDTO jobPageQueryDTO = new JobPageQueryDTO(page, pageSize, type, category);
        return jobService.pageQuery(jobPageQueryDTO);
    }

    /**
     * 根据岗位id返回
     * @return 单个岗位信息
     */
    @GetMapping("/queryById")
    @ApiOperation("根据岗位id返回")
    public Result queryById(@RequestParam("id") Long id){
        return jobService.queryById(id);
    }

    /**
     * 模糊查询Job信息
     * @param  jobFuzzyQueryDTO 模糊查询条件
     * @return 模糊查询结果
     */
    @GetMapping("/fuzzyQuery")
    @ApiOperation("模糊查询Job信息")
    public Result fuzzyQuery(@RequestParam(required = false) String keyWord,
                             @RequestParam(required = false) Integer page,
                             @RequestParam(required = false) Integer pageSize){
        JobFuzzyQueryDTO jobFuzzyQueryDTO = new JobFuzzyQueryDTO(keyWord, page, pageSize);
        return jobService.fuzzyQuery(jobFuzzyQueryDTO);
    }

    /**
     * 投递简历
     * @param id 向id的工作投递简历，传入的是Job的id
     * @return
     */
    @PostMapping("/deliverResume")
    @ApiOperation("投递简历")
    public Result deliverResume(@RequestParam("id")Long id){
        return jobService.deliverResume(id);
    }
}
