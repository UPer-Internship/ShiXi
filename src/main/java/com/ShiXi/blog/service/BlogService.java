package com.ShiXi.blog.service;

import com.ShiXi.blog.domin.dto.MyBlogListPageQueryReqDTO;
import com.ShiXi.blog.domin.dto.UserBlogListPageQueryReqDTO;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.blog.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogService extends IService<Blog> {

    Result publishBlog(String title, String content,MultipartFile cover, List<MultipartFile> images);

    Result pageQueryMyBlog(MyBlogListPageQueryReqDTO reqDTO);

    Result queryBlogById(Long id);

    Result queryBlogListByUserId(UserBlogListPageQueryReqDTO reqDTO);
}
