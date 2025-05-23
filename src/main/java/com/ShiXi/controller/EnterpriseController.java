package com.ShiXi.controller;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.Job;
import com.ShiXi.service.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {
    @Resource
    EnterpriseService enterpriseService;

    /**
     * 发布岗位 需要传入一个完整的job类 除了id和userId
     * @param job 岗位类
     * @return
     */
    @PostMapping("/pubJob")
    Result pubJob(@RequestBody Job job) {
        return enterpriseService.pubJob(job);
    }

    /**
     * 删除一个岗位 需要传入这个岗位的id
     * @param id
     * @return
     */
    @PostMapping("/deleteJob")
    Result pubJob(@RequestParam("id")Long id) {
        return enterpriseService.deleteJob(id);
    }
    /**
     * 根据id查询某个岗位
     */
    @GetMapping("/queryPubById")
    Result queryPubNyId(@RequestParam("id")Long id) {
        return enterpriseService.queryPubById(id);
    }

    /**
     * 查询自己已经发布全部岗位
     * @return
     */
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
    @PostMapping("/updateJob")
    Result updateJob(@RequestBody Job job,@RequestParam("id")Long id) {
        return enterpriseService.updateJob(job,id);
    }

    /**
     * 查询收到的所有简历
     * @return 所有简历
     */
    @GetMapping("/queryResumeList")
    Result queryResumeList() {
        return enterpriseService.queryResumeList();
    }

    /**
     * 根据id查询某个简历
     * @param id 要查询的简历的id
     * @return 该简历
     */
    @GetMapping("/queryResumeById")
    Result queryResumeById(@RequestParam("id")Long id) {
        return enterpriseService.queryResumeById(id);
    }
}
