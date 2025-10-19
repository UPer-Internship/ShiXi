package com.ShiXi.blog.controller;

import com.ShiXi.blog.domin.dto.MyBlogListPageQueryReqDTO;
import com.ShiXi.blog.domin.dto.UserBlogListPageQueryReqDTO;
import com.ShiXi.blog.service.BlogLikeService;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.common.domin.dto.UserDTO;
import com.ShiXi.blog.entity.Blog;
import com.ShiXi.blog.service.BlogService;
import com.ShiXi.common.utils.SystemConstants;
import com.ShiXi.common.utils.UserHolder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {
    @Resource
    private BlogService blogService;
    @Resource
    private BlogLikeService blogLikeService;
    @PostMapping("/publish")
    public Result publishBlog(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("cover") MultipartFile cover,
            @RequestParam("images") List<MultipartFile> images  // 接收多文件
             ) {
        return blogService.publishBlog(title,content,cover,images);

    }

    @GetMapping("/queryMyBlogList")
    public Result pageQueryMyBlog(@RequestBody MyBlogListPageQueryReqDTO reqDTO) {
        return blogService.pageQueryMyBlog(reqDTO);
    }

    @GetMapping("/queryBlogListByUserId")

    public Result queryBlogListByUserId(@RequestBody UserBlogListPageQueryReqDTO reqDTO) {
        return blogService.queryBlogListByUserId(reqDTO);
    }


    @GetMapping("/queryBlogById")
    private Result queryBlogById(@RequestParam("id") Long id) {
        return blogService.queryBlogById(id);
    }
    @PutMapping("/likeBlog")
    public Result likeBlog(@RequestParam("id") Long id) {
        // 修改点赞数量
        return blogLikeService.likeBlog(id);
    }

    @GetMapping("/me/isLikedBlog")
    private Result isLikedBlog(@RequestParam("id") Long id) {
        boolean isLiked = blogLikeService.isLikedBlog(id);
        return Result.ok(isLiked);
    }


}
