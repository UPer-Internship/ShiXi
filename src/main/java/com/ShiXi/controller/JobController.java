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
     * 
     * @return 分页查询结果
     */
    @GetMapping("/pageQuery")
    @ApiOperation("分页且按条件查询岗位")
    public Result pageQuery(@RequestParam(required = false) Integer page, // 页码
            @RequestParam(required = false) Integer pageSize, // 每页记录数
            @RequestParam(required = false) String type, // 岗位类型(实现、兼职、科研课题)
            @RequestParam(required = false) String category, // 岗位类别(UI设计等tag)
            @RequestParam(required = false) String industry, // 行业（如互联网、金融等）
            @RequestParam(required = false) Double salaryMin, // 薪资下限
            @RequestParam(required = false) Double salaryMax, // 薪资上限
            @RequestParam(required = false) String onboardTime, // 到岗时间（如立即、1周内等）
            @RequestParam(required = false) String tag// 岗位标签，如“线下-可转正”，用-分割
    ) {
        // 处理空字符串为null
        type = (type != null && type.trim().isEmpty()) ? null : type;
        category = (category != null && category.trim().isEmpty()) ? null : category;
        industry = (industry != null && industry.trim().isEmpty()) ? null : industry;
        onboardTime = (onboardTime != null && onboardTime.trim().isEmpty()) ? null : onboardTime;
        tag = (tag != null && tag.trim().isEmpty()) ? null : tag;

        JobPageQueryDTO jobPageQueryDTO = new JobPageQueryDTO(page, pageSize, type, category, industry, salaryMin,
                salaryMax, onboardTime, tag);
        return jobService.pageQuery(jobPageQueryDTO);
    }

    /**
     * 根据岗位id返回
     * 
     * @return 单个岗位信息
     */
    @GetMapping("/queryById")
    @ApiOperation("根据岗位id返回")
    public Result queryById(@RequestParam("id") Long id) {
        return jobService.queryById(id);
    }

    /**
     * 模糊查询Job信息
     * 
     * @param keyWord 模糊查询条件
     * @return 模糊查询结果
     */
    @GetMapping("/fuzzyQuery")
    @ApiOperation("模糊查询Job信息")
    public Result fuzzyQuery(@RequestParam(required = false) String keyWord,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {
        JobFuzzyQueryDTO jobFuzzyQueryDTO = new JobFuzzyQueryDTO(keyWord, page, pageSize);
        return jobService.fuzzyQuery(jobFuzzyQueryDTO);
    }

    /**
     * 投递简历
     * 
     * @param id 向id的工作投递简历，传入的是Job的id
     * @return
     */
    @PostMapping("/deliverResume")
    @ApiOperation("投递简历")
    public Result deliverResume(@RequestParam("id") Long id) {
        return jobService.deliverResume(id);
    }
}
