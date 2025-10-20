package com.ShiXi.comment.service;

import com.ShiXi.blog.entity.BlogLike;
import com.ShiXi.comment.domin.dto.pageQueryBlogCommentReqDTO;
import com.ShiXi.comment.entity.Comment;
import com.ShiXi.common.domin.dto.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface CommentService extends IService<Comment> {
    Result postComment(String content, Long blogId, MultipartFile image);

    Result deleteComment(Long commentId);

    Result getCommentById(Long commentId);

    Result pageQueryCommentByBlog(pageQueryBlogCommentReqDTO reqDTO);
}
