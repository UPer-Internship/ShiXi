package com.ShiXi.follow.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.follow.entity.Follow;
import com.ShiXi.common.mapper.FollowMapper;
import com.ShiXi.follow.service.FollowService;
import com.ShiXi.user.common.service.UserService;
import com.ShiXi.common.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private UserService userService;



    @Override
    public boolean isMeFollowed(Long bloggerId) {
        // 1.获取登录用户
        Long userId = UserHolder.getUser().getId();
        Long count = lambdaQuery().eq(Follow::getBloggerId, bloggerId)
                .eq(Follow::getFollowerId, userId)
                .count();
        // 3.判断
        return count > 0;
    }



    @Override
    public Result follow(Long bloggerId) {
        Long userId = UserHolder.getUser().getId();
        boolean isFollowed = isMeFollowed(bloggerId);
        if(isFollowed){
            // 3. 已点赞 → 取消点赞（删除记录）
            LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Follow::getFollowerId, userId)
                    .eq(Follow::getBloggerId, bloggerId);
            remove(queryWrapper);
            return Result.ok( "取消关注成功"); // 返回操作后的状态（未点赞）
        }
        else{
            // 4. 未点赞 → 执行点赞（新增记录）
            Follow follow = new Follow();
            follow.setFollowerId(userId);
            follow.setBloggerId(bloggerId);
            save(follow);
            return Result.ok("关注成功"); // 返回操作后的状态（已点赞）
        }
    }

    @Override
    public Result myFollowerList() {
        Long userId = UserHolder.getUser().getId();
        List<Follow> followList = lambdaQuery().eq(Follow::getBloggerId, userId).list();
        return Result.ok(followList);
    }

    @Override
    public Result UserFollowerList(Long userId) {
        List<Follow> followList = lambdaQuery().eq(Follow::getBloggerId, userId).list();
        return Result.ok(followList);
    }

    @Override
    public Result myFollowerAmount() {
        Long userId = UserHolder.getUser().getId();
        Long count = lambdaQuery().eq(Follow::getBloggerId, userId).count();
        return Result.ok(count);
    }

    @Override
    public Result userFollowerAmount(Long userId) {
        Long count = lambdaQuery().eq(Follow::getBloggerId, userId).count();
        return Result.ok(count);
    }
}
