package com.ShiXi.enterprise.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.job.entity.Job;
import com.ShiXi.enterprise.service.EnterpriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/enterprise")
@Api(tags = "发布岗位相关接口")
public class EnterpriseController {
    @Resource
    EnterpriseService enterpriseService;

    /**
     * 发布岗位 需要传入一个完整的job类 除了id和userId
     * @param job 岗位类
     * @return
     */
    @ApiOperation("发布岗位")
    @PostMapping("/pubJob")
    Result pubJob(@RequestBody Job job) {
        return enterpriseService.pubJob(job);
    }

    /**
     * 删除一个岗位 需要传入这个岗位的id
     * @param id
     * @return
     */
    @ApiOperation("删除岗位")
    @PostMapping("/deleteJob")
    Result pubJob(@RequestParam("id")Long id) {
        return enterpriseService.deleteJob(id);
    }
    /**
     * 根据id查询某个岗位
     */
    @ApiOperation("根据id查询某个岗位")
    @GetMapping("/queryPubById")
    Result queryPubNyId(@RequestParam("id")Long id) {
        return enterpriseService.queryPubById(id);
    }

    /**
     * 查询自己已经发布全部岗位
     * @return
     */
    @ApiOperation("查询自己已经发布全部岗位")
    @GetMapping("/queryMyPubList")
    Result queryMyPubList() {
        return enterpriseService.queryMyPubList();
    }

    /**
     * 更新某个岗位 需要传入更新后的job类 还有这个job的id
     * @param job 用于更新的job信息
     * @param id 要更新的job的id
     * @return
     */
    @ApiOperation("更新某个岗位")
    @PostMapping("/updateJob")
    Result updateJob(@RequestBody Job job,@RequestParam("id")Long id) {
        return enterpriseService.updateJob(job,id);
    }

    /**
     * 改变岗位状态
     */
    @ApiOperation("改变岗位状态")
    @PostMapping("/changeJobStatus")
    public Result changeJobStatus(@RequestParam("jobId") Long jobId, @RequestParam("status") Integer status) {
        return enterpriseService.changeJobStatus(jobId, status);
    }

    /**
     * 查询收到的所有简历
     * @return 所有简历
     */
    @ApiOperation("查询收到的所有简历")
    @GetMapping("/queryResumeList")
    Result queryResumeList() {
        return enterpriseService.queryResumeList();
    }

    /**
     * 根据id查询某个简历
     * @param id 要查询的简历的id
     * @return 该简历
     */
    @ApiOperation("根据id查询某个简历")
    @GetMapping("/queryResumeById")
    Result queryResumeById(@RequestParam("id")Long id) {
        return enterpriseService.queryResumeById(id);
    }
}
