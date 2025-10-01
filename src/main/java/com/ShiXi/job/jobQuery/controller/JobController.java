package com.ShiXi.job.jobQuery.controller;

import com.ShiXi.job.jobQuery.domin.dto.EsJobQueryDTO;
import com.ShiXi.job.jobQuery.domin.dto.JobFuzzyQueryDTO;
import com.ShiXi.job.jobQuery.domin.dto.JobPageQueryDTO;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.job.jobQuery.service.JobService;
import com.ShiXi.job.jobQuery.service.impl.MysqlJobServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/job")
@Tag(name = "废弃")
public class JobController {
    @Resource
    private JobService jobService;
    @Resource
    private MysqlJobServiceImpl mysqlJobServiceImpl;

    @PostMapping("/esQuery")
    public Result queryJobEs(@RequestBody EsJobQueryDTO esJobQueryDTO) {
        return mysqlJobServiceImpl.queryJob(esJobQueryDTO);
    }

    @PostMapping("/saveSearchHistory")
    public Result saveSearchHistory(@RequestParam String word) {
        return mysqlJobServiceImpl.saveSearchHistory(word);
    }

    /**
     * 分页且按条件查询岗位
     *
     * @return 分页查询结果
     */

    @GetMapping("/pageQuery")
    @Operation(summary = "分页且按条件查询岗位")
    public Result pageQuery(@Parameter(description = "页码") @RequestParam(required = false) Integer page, // 页码
                            @Parameter(description = "每页记录数") @RequestParam(required = false) Integer pageSize, // 每页记录数
                            @Parameter(description = "岗位类型(实现、兼职、科研课题)") @RequestParam(required = false) String type, // 岗位类型(实现、兼职、科研课题)
                            @Parameter(description = "岗位类别(UI设计等tag)") @RequestParam(required = false) String category, // 岗位类别(UI设计等tag)
                            @Parameter(description = "行业（如互联网、金融等）") @RequestParam(required = false) String industry, // 行业（如互联网、金融等）
                            @Parameter(description = "薪资下限") @RequestParam(required = false) Double salaryMin, // 薪资下限
                            @Parameter(description = "薪资上限") @RequestParam(required = false) Double salaryMax, // 薪资上限
                            @Parameter(description = "到岗时间（如立即、1周内等）") @RequestParam(required = false) String onboardTime, // 到岗时间（如立即、1周内等）
                            @Parameter(description = "岗位标签，如“线下-可转正”，用-分割") @RequestParam(required = false) String tag,// 岗位标签，如“线下-可转正”，用-分割
                            @Parameter(description = "实习总时长") @RequestParam(required = false) String totalTime// 实习总时长
    ) {
        // 处理空字符串为null
        type = (type != null && type.trim().isEmpty()) ? null : type;
        category = (category != null && category.trim().isEmpty()) ? null : category;
        industry = (industry != null && industry.trim().isEmpty()) ? null : industry;
        onboardTime = (onboardTime != null && onboardTime.trim().isEmpty()) ? null : onboardTime;
        tag = (tag != null && tag.trim().isEmpty()) ? null : tag;
        totalTime = (totalTime != null && totalTime.trim().isEmpty()) ? null : totalTime;

        JobPageQueryDTO jobPageQueryDTO = new JobPageQueryDTO(page, pageSize, type, category, industry, salaryMin,
                salaryMax, tag);
        return jobService.pageQuery(jobPageQueryDTO);
    }

    /**
     * 根据岗位id返回
     *
     * @return 单个岗位信息
     */
    @GetMapping("/queryById")
    @Operation(summary = "根据岗位id返回")
    public Result queryById(@Parameter(description = "岗位ID") @RequestParam("id") Long id) {
        return jobService.queryById(id);
    }


    /**
     * 模糊查询Job信息
     *
     * @param keyWord 模糊查询条件
     * @return 模糊查询结果
     */
    @GetMapping("/fuzzyQuery")
    @Operation(summary = "模糊查询Job信息")
    public Result fuzzyQuery(@Parameter(description = "模糊查询条件") @RequestParam(required = false) String keyWord,
                             @Parameter(description = "页码") @RequestParam(required = false) Integer page,
                             @Parameter(description = "每页记录数") @RequestParam(required = false) Integer pageSize) {
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
    @Operation(summary = "投递简历")
    public Result deliverResume(@Parameter(description = "工作ID") @RequestParam("id") Long id) {
        return jobService.deliverResume(id);
    }
}
