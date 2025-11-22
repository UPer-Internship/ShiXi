package com.ShiXi.comment.service;

import com.ShiXi.blog.entity.BlogLike;
import com.ShiXi.comment.domin.dto.IsCommentLikeReqDTO;
import com.ShiXi.comment.entity.CommentLike;
import com.ShiXi.common.domin.dto.Result;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CommentLikeService extends IService<CommentLike>{
    boolean isCommentLike(IsCommentLikeReqDTO reqDTO);

    Result likeComment(IsCommentLikeReqDTO reqDTO);
}
