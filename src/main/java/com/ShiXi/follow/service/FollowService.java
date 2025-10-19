package com.ShiXi.follow.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.follow.entity.Follow;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FollowService extends IService<Follow> {


    boolean isMeFollowed(Long followUserId);

    Result follow(Long bloggerId);

    Result myFollowerList();

    Result UserFollowerList(Long userId);

    Result myFollowerAmount();

    Result userFollowerAmount(Long userId);
}
