package com.ShiXi.blog.service.impl;

import com.ShiXi.blog.entity.Blog;
import com.ShiXi.blog.entity.BlogLike;
import com.ShiXi.blog.service.BlogLikeService;
import com.ShiXi.blog.service.BlogService;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.BlogLikeMapper;
import com.ShiXi.common.mapper.BlogMapper;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.common.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class BlogLikeServiceImpl extends ServiceImpl<BlogLikeMapper, BlogLike> implements BlogLikeService {
    @Override
    public boolean isLikedBlog(Long id) {
        Long userId = UserHolder.getUser().getId();

        LambdaQueryWrapper<BlogLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlogLike::getUserId, userId)
                .eq(BlogLike::getBlogId, id);
        return baseMapper.selectCount(queryWrapper) > 0;


    }

    @Override
    public Result likeBlog(Long id) {
        Long userId = UserHolder.getUser().getId();
        boolean isLiked = isLikedBlog(id);
        if (isLiked) {
            // 3. 已点赞 → 取消点赞（删除记录）
            LambdaQueryWrapper<BlogLike> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BlogLike::getUserId, userId)
                    .eq(BlogLike::getBlogId, id);
            remove(queryWrapper);
            return Result.ok( "取消点赞成功"); // 返回操作后的状态（未点赞）
        } else {
            // 4. 未点赞 → 执行点赞（新增记录）
            BlogLike blogLike = new BlogLike();
            blogLike.setUserId(userId);
            blogLike.setBlogId(id);
            save(blogLike);
            return Result.ok("点赞成功"); // 返回操作后的状态（已点赞）
        }
    }
}
