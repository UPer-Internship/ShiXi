package com.ShiXi.comment.controller;

import com.ShiXi.comment.domin.dto.IsCommentLikeReqDTO;
import com.ShiXi.comment.service.CommentLikeService;
import com.ShiXi.comment.service.CommentService;
import com.ShiXi.common.domin.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/commentLike")
public class CommentLikeController {
    @Resource
    private CommentLikeService commentLikeService;
    @PostMapping("/likeComment")
    public Result deleteComment(@RequestParam IsCommentLikeReqDTO reqDTO) {
        return commentLikeService.likeComment(reqDTO);
    }
    @GetMapping("/isCommentLike")
    public Result isCommentLike(@RequestParam IsCommentLikeReqDTO reqDTO) {
        boolean isLiked= commentLikeService.isCommentLike(reqDTO);
        return Result.ok(isLiked);
    }
}
