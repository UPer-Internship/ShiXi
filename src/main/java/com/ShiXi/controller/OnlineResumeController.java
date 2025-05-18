package com.ShiXi.controller;

import com.ShiXi.dto.Result;
import com.ShiXi.service.OnlineResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 在线简历相关接口
 */
@Slf4j
@RestController
@RequestMapping("/student")
public class OnlineResumeController {
    @Resource
    private OnlineResumeService onlineResumeService;
    /**
     * 获取当前用户在线简历信息
     * @return 在线简历信息的vo类
     */
    @GetMapping("/me/myInfo")
    public Result getOnlineResume() {
        return onlineResumeService.getOnlineResume();
    }
}
