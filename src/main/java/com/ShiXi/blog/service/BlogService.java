package com.ShiXi.blog.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.blog.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BlogService extends IService<Blog> {
    Result saveBlog(Blog blog);

    Result likeBlog(Long id);

    Result queryHotBlog(Integer current);

    Result queryBlogById(Long id);

    Result queryBlogLikes(Long id);

    Result queryBlogOfFollow(Long max, Integer offset);
}
