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
    @GetMapping("/myFollowerAmount")
    public Result myFollowerAmount() {
        return followService.myFollowerAmount();
    }
    @GetMapping("/userFollowerAmount")
    public Result userFollowerAmount(Long userId) {
        return followService.userFollowerAmount(userId);
    }

    @GetMapping("/myFollowerList")
    public Result myFollowerList() {
        return followService.myFollowerList();
    }
    @GetMapping("/UserFollowerList")
    public Result UserFollowerList(@RequestParam Long userId) {
        return followService.UserFollowerList(userId);
    }


}
