package com.ShiXi.job.jobPublish.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.job.jobQuery.entity.Job;
import com.ShiXi.job.jobPublish.service.JobPublishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/enterprise")
@Tag(name = "发布岗位相关接口")
public class JobPublishController {
    @Resource
    JobPublishService jobPublishService;

    /**
     * 发布岗位 需要传入一个完整的job类 除了id和userId
     * @param job 岗位类
     * @return
     */
    @Operation(summary = "发布岗位")
    @PostMapping("/pubJob")
    Result pubJob(@RequestBody Job job) {
        return jobPublishService.pubJob(job);
    }

    /**
     * 删除一个岗位 需要传入这个岗位的id
     * @param id
     * @return
     */
    @Operation(summary ="删除岗位")
    @PostMapping("/deleteJob")
    Result pubJob(@RequestParam("id")Long id) {
        return jobPublishService.deleteJob(id);
    }
    /**
     * 根据id查询某个岗位
     */
    @Operation(summary ="根据id查询某个岗位")
    @GetMapping("/queryPubById")
    Result queryPubNyId(@RequestParam("id")Long id) {
        return jobPublishService.queryPubById(id);
    }

    /**
     * 查询自己已经发布全部岗位
     * @return
     */
    @Operation(summary ="查询自己已经发布全部岗位")
    @GetMapping("/queryMyPubList")
    Result queryMyPubList() {
        return jobPublishService.queryMyPubList();
    }

    /**
     * 更新某个岗位 需要传入更新后的job类 还有这个job的id
     * @param job 用于更新的job信息
     * @param id 要更新的job的id
     * @return
     */
    @Operation(summary ="更新某个岗位")
    @PostMapping("/updateJob")
    Result updateJob(@RequestBody Job job,@RequestParam("id")Long id) {
        return jobPublishService.updateJob(job,id);
    }

    /**
     * 改变岗位状态
     */
    @Operation(summary ="改变岗位状态")
    @PostMapping("/changeJobStatus")
    public Result changeJobStatus(@RequestParam("jobId") Long jobId, @RequestParam("status") Integer status) {
        return jobPublishService.changeJobStatus(jobId, status);
    }

    /**
     * 查询收到的所有简历
     * @return 所有简历
     */
    @Operation(summary ="查询收到的所有简历")
    @GetMapping("/queryResumeList")
    Result queryResumeList() {
        return jobPublishService.queryResumeList();
    }

    /**
     * 查询新投递的简历（从Redis消费）
     * @return 新投递的简历列表
     */
    @Operation(summary ="查询新投递的简历")
    @GetMapping("/queryNewResumeList")
    Result queryNewResumeList() {
        return jobPublishService.queryNewResumeList();
    }

    /**
     * 查询历史全量简历（分页查询）
     * @param page 页码，默认为1
     * @param pageSize 每页大小，默认为10
     * @return 分页查询结果
     */
    @Operation(summary ="查询历史全量简历")
    @GetMapping("/queryHistoryResumeList")
    Result queryHistoryResumeList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return jobPublishService.queryHistoryResumeList(page, pageSize);
    }

    /**
     * 根据id查询某个简历
     * @param id 要查询的简历的id
     * @return 该简历
     */
    @Operation(summary ="根据id查询某个简历")
    @GetMapping("/queryResumeById")
    Result queryResumeById(@RequestParam("id")Long id) {
        return jobPublishService.queryResumeById(id);
    }
}
