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
    //发布岗位 需要传入一个完整的job类 除了id和userId
    @PostMapping("/pubJob")
    Result pubJob(@RequestBody Job job) {
        return enterpriseService.pubJob(job);
    }
    //删除一个岗位 需要传入这个岗位的id
    @PostMapping("/deleteJob")
    Result pubJob(@RequestParam("id")Long id) {
        return enterpriseService.deleteJob(id);
    }
    //根据id查询某个岗位
    @GetMapping("/queryPubById")
    Result queryPubNyId(@RequestParam("id")Long id) {
        return enterpriseService.queryPubById(id);
    }
    //查询自己已经发布全部岗位
    @GetMapping("/queryMyPubList")
    Result queryMyPub() {
        return enterpriseService.queryMyPubList();
    }
    //更新某个岗位 需要传入更新后的job类 还有这个job的id
    @PostMapping("/updateJob")
    Result updateJob(@RequestBody Job job,@RequestParam("id")Long id) {
        return enterpriseService.updateJob(job,id);
    }
}
