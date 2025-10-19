package com.ShiXi.follow.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.follow.service.FollowService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/follow")
public class FollowController {

    @Resource
    private FollowService followService;


    @GetMapping("/isMeFollowed")
    public Result isMeFollowed(@RequestParam Long bloggerId ) {
        boolean isFollowed= followService.isMeFollowed(bloggerId);
        return Result.ok(isFollowed);
    }

    @PutMapping("/follow")
    public Result follow(@RequestParam Long bloggerId) {
        return followService.follow(bloggerId);
    }

    @GetMapping("/myFollowerList")
    public Result myFollowerList() {
    }
    @GetMapping("/UserFollowerList")
    public Result UserFollowerList() {
    }
}
