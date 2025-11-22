package com.ShiXi.comment.service.impl;

import com.ShiXi.blog.entity.BlogLike;
import com.ShiXi.blog.service.BlogLikeService;
import com.ShiXi.comment.domin.dto.IsCommentLikeReqDTO;
import com.ShiXi.comment.entity.CommentLike;
import com.ShiXi.comment.service.CommentLikeService;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.BlogLikeMapper;
import com.ShiXi.common.mapper.CommentLikeMapper;
import com.ShiXi.common.utils.UserHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommentLikeServiceImpl extends ServiceImpl<CommentLikeMapper, CommentLike> implements CommentLikeService {

    @Override
    public boolean isCommentLike(IsCommentLikeReqDTO reqDTO) {
        Long userId = UserHolder.getUser().getId();
        if(reqDTO.getReplyId()!=null){
            Long count = lambdaQuery().eq(CommentLike::getCommentId, reqDTO.getCommentId())
                    .eq(CommentLike::getUserId, userId)
                    .eq(CommentLike::getReplyId, reqDTO.getReplyId())
                    .count();
            return count > 0;
        }
        else{
            Long count = lambdaQuery().eq(CommentLike::getCommentId, reqDTO.getCommentId())
                    .eq(CommentLike::getUserId, userId)
                    .isNull(CommentLike::getReplyId)
                    .count();
            return count > 0;
        }

    }

    @Override
    public Result likeComment(IsCommentLikeReqDTO reqDTO) {
        boolean isLiked = isCommentLike(reqDTO);
        Long userId = UserHolder.getUser().getId();
        //取消点赞
        if(isLiked){
            //二级评论
            if(reqDTO.getReplyId()!=null){
                lambdaUpdate().eq(CommentLike::getCommentId, reqDTO.getCommentId())
                        .eq(CommentLike::getUserId, userId)
                        .eq(CommentLike::getReplyId, reqDTO.getReplyId())
                        .remove();
                return Result.ok();
            }
            //一级评论
            lambdaUpdate().eq(CommentLike::getCommentId, reqDTO.getCommentId())
                    .eq(CommentLike::getUserId, userId)
                    .isNull(CommentLike::getReplyId)
                    .remove();
            return Result.ok();
        }
        //点赞
        else{
            if(reqDTO.getReplyId()!=null){
                CommentLike commentLike = new CommentLike();
                commentLike.setCommentId(reqDTO.getCommentId())
                        .setReplyId(reqDTO.getReplyId())
                        .setUserId(userId);
                save(commentLike);
                return Result.ok(commentLike);
            }
            //一级评论
            CommentLike commentLike = new CommentLike();
            commentLike.setCommentId(reqDTO.getCommentId())
                    .setUserId(userId);
            save(commentLike);
            return Result.ok(commentLike);
        }
    }
}
