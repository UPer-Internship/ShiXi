package com.ShiXi.comment.controller;

import com.ShiXi.comment.domin.dto.pageQueryBlogCommentReqDTO;
import com.ShiXi.comment.service.CommentService;
import com.ShiXi.common.domin.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @PostMapping("/postComment")
    public Result postComment(
            @RequestParam("content") String content,
            @RequestParam("blogId") Long blogId,
            @RequestParam("image") MultipartFile image
    ) {
        return commentService.postComment(content, blogId, image);
    }

    @PostMapping("/deleteComment")
    public Result deleteComment(@RequestParam("commentId") Long commentId) {
        return commentService.deleteComment(commentId);
    }

    @GetMapping("/getCommentById")
    public Result getCommentById(@RequestParam Long commentId) {
        return commentService.getCommentById(commentId);
    }
    @GetMapping("/pageQueryCommentByBlog")
    public Result pageQueryCommentByBlog(@RequestBody pageQueryBlogCommentReqDTO reqDTO) {
        return commentService.pageQueryCommentByBlog(reqDTO);
    }
}
