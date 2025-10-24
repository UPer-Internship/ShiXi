package com.ShiXi.comment.service;

import com.ShiXi.blog.entity.BlogLike;
import com.ShiXi.comment.domin.dto.pageQueryCommentReplyReqDTO;
import com.ShiXi.comment.entity.CommentReply;
import com.ShiXi.common.domin.dto.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface CommentReplyService extends IService<CommentReply> {
    Result replyComment(String content, Long blogId, MultipartFile image,Long commentId,Long toUserId);

    Result deleteReply(Long replyId);

    Result pageQueryCommentReply(pageQueryCommentReplyReqDTO reqDTO);
}
