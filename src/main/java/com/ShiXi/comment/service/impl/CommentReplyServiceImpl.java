package com.ShiXi.comment.service.impl;

import com.ShiXi.blog.entity.BlogLike;
import com.ShiXi.blog.service.BlogLikeService;
import com.ShiXi.comment.domin.dto.pageQueryCommentReplyReqDTO;
import com.ShiXi.comment.entity.Comment;
import com.ShiXi.comment.entity.CommentReply;
import com.ShiXi.comment.service.CommentReplyService;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.BlogLikeMapper;
import com.ShiXi.common.mapper.CommentReplyMapper;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.common.utils.UserHolder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@Service
public class CommentReplyServiceImpl extends ServiceImpl<CommentReplyMapper, CommentReply> implements CommentReplyService {
    @Resource
    private OSSUploadService ossUploadService;
    private final static String COMMENT_IMAGE = "comment_image/";
    private final static String BLOG_PREFIX = "blog/";
    private final static String COMMENT_PREFIX = "comment/";
    private final static String USER_PREFIX = "user/";
    private final static String REPLY_PREFIX = "reply/";
    @Override
    //TODO 通知被回复者
    public Result replyComment(String content, Long blogId, MultipartFile image, Long commentId,Long toUserId) {
        Long userId = UserHolder.getUser().getId();
        CommentReply commentReply = new CommentReply();
        commentReply.setContent(content);
        commentReply.setCommentId(commentId);
        commentReply.setUserId(userId);
        commentReply.setToUserId(toUserId);
        boolean save = save(commentReply);
        if(!save){
            return Result.fail("回复失败");
        }
        Long replyId = commentReply.getId();
        String imageUrl=null;
        try {
            if (image != null) {
                String commentImageOssDir = COMMENT_IMAGE + BLOG_PREFIX + blogId + "/" + COMMENT_PREFIX + commentId + "/" + REPLY_PREFIX+replyId+"/"+USER_PREFIX + userId;
                imageUrl = ossUploadService.uploadPicture(image, commentImageOssDir);
                commentReply.setImage(imageUrl);
            }
            return Result.ok(replyId);
        } catch (Exception e) {
            ossUploadService.deletePicture(imageUrl);
            throw new RuntimeException("图片上传失败");
        }
    }

    @Override
    public Result deleteReply(Long replyId) {
        //TODO 点赞记录
        removeById(replyId);
        return Result.ok();
    }

    @Override
    public Result pageQueryCommentReply(pageQueryCommentReplyReqDTO reqDTO) {

        Page<CommentReply> page = new Page<>(
                reqDTO.getPageNum(),  // 当前页码
                reqDTO.getPageSize() // 每页条数
        );

        // 2. 构建查询条件 + 排序
        Page<CommentReply> commentPage = lambdaQuery()
                .eq(CommentReply::getCommentId, reqDTO.getCommentId()) // 筛选目标博客的评论
                .orderByDesc(CommentReply::getLikeCount)         // 第一排序：点赞数从高到低
                .orderByDesc(CommentReply::getUpdateTime)        // 第二排序：更新时间从新到旧
                .page(page); // 执行分页查询

        return Result.ok(commentPage);
    }
}
