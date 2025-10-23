package com.ShiXi.comment.controller;

import com.ShiXi.comment.domin.dto.pageQueryCommentReplyReqDTO;
import com.ShiXi.comment.service.CommentReplyService;
import com.ShiXi.comment.service.CommentService;
import com.ShiXi.common.domin.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/commentReply")
public class CommentReplyController {
    @Resource
    private CommentReplyService commentReplyService;

    @PostMapping("/replyComment")
    public Result replyComment(
            @RequestParam("content") String content,
            @RequestParam("blogId") Long blogId,
            @RequestParam("image") MultipartFile image,
            @RequestParam("commentId") Long commentId,
            @RequestParam("toUserId") Long toUserId
    ) {
        return commentReplyService.replyComment(content, blogId, image,commentId,toUserId);
    }

    @PostMapping("/deleteReply")
    public Result replyComment(
            @RequestParam("replyId") Long replyId
    ) {
        return commentReplyService.deleteReply(replyId);
    }

    @PostMapping("/pageQueryCommentReply")
    public Result pageQueryCommentReply(
            @RequestBody pageQueryCommentReplyReqDTO reqDTO
    ) {
        return commentReplyService.pageQueryCommentReply(reqDTO);
    }
}
