package com.ShiXi.blog.controller;

import com.ShiXi.blog.ScheduledJob.ScoreCalculator;
import com.ShiXi.blog.domin.dto.HotBlogPageQueryReqDTO;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/blog")
@Tag(name = "帖子相关接口")
public class BlogController {
    @Resource
    private BlogService blogService;
    @Resource
    private BlogLikeService blogLikeService;
    @Resource
    private ScoreCalculator scoreCalculator;
    @PostMapping("/publish")
    @Operation(summary = "帖子发布接口，标题，正文，封面图片，内容图片（允许9张）")
    public Result publishBlog(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("cover") MultipartFile cover,
            @RequestParam("images") List<MultipartFile> images  // 接收多文件
             ) {
        return blogService.publishBlog(title,content,cover,images);

    }

    @PostMapping("/queryHotBlogs")
    public Result queryHotBlogs(@RequestBody HotBlogPageQueryReqDTO reqDTO){
        return blogService.queryHotBlogs(reqDTO);
    }


    @GetMapping("/queryMyBlogList")
    @Operation(summary = "查询我发布的帖子列表，有页号和页面大小两个参数pageNum pageSize")
    public Result pageQueryMyBlog(@RequestBody MyBlogListPageQueryReqDTO reqDTO) {
        return blogService.pageQueryMyBlog(reqDTO);
    }

    @PutMapping("/deleteBlog")
    @Operation(summary = "根据帖子的id删除我发布的帖子")
    public Result deleteBlog(@RequestParam("id") Long id) {
        // 修改点赞数量
        return blogService.deleteBlog(id);
    }

    @GetMapping("/queryBlogListByUserId")
    @Operation(summary = "查询某个用户发布的帖子列表，userId pageNum pageSize")
    public Result queryBlogListByUserId(@RequestBody UserBlogListPageQueryReqDTO reqDTO) {
        return blogService.queryBlogListByUserId(reqDTO);
    }

    @Operation(summary = "根据帖子id查询帖子")
    @GetMapping("/queryBlogById")
    private Result queryBlogById(@RequestParam("id") Long id) {
        return blogService.queryBlogById(id);
    }
    @Operation(summary = "根据帖子id给帖子点赞或者取消点赞")
    @PutMapping("/likeBlog")
    public Result likeBlog(@RequestParam("id") Long id) {
        // 修改点赞数量
        return blogLikeService.likeBlog(id);
    }
    @Operation(summary = "查询我是否给某个帖子点过赞")
    @GetMapping("/me/isLikedBlog")
    private Result isLikedBlog(@RequestParam("id") Long id) {
        boolean isLiked = blogLikeService.isLikedBlog(id);
        return Result.ok(isLiked);
    }

    @GetMapping("/testSch")
    private Result testSch() throws Exception {

        scoreCalculator.ScoreCulJobHandler();
        return Result.ok();
    }

}
