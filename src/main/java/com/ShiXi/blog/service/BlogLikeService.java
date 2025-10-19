package com.ShiXi.blog.service;

import com.ShiXi.blog.entity.Blog;
import com.ShiXi.blog.entity.BlogLike;
import com.ShiXi.common.domin.dto.Result;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BlogLikeService extends IService<BlogLike> {
    boolean isLikedBlog(Long id);

    Result likeBlog(Long id);
}
