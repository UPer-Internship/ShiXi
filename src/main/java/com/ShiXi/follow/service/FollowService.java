package com.ShiXi.follow.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.follow.entity.Follow;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FollowService extends IService<Follow> {

    Result follow(Long followUserId, Boolean isFollow);

    Result isFollow(Long followUserId);

    Result followCommons(Long id);
}
