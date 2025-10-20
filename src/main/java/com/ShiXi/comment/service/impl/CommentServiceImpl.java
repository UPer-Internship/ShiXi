package com.ShiXi.comment.service.impl;

import cn.hutool.core.bean.BeanUtil;

import com.ShiXi.blog.entity.Blog;
import com.ShiXi.blog.entity.BlogLike;
import com.ShiXi.blog.service.BlogLikeService;
import com.ShiXi.blog.service.BlogService;
import com.ShiXi.comment.domin.dto.pageQueryBlogCommentReqDTO;
import com.ShiXi.comment.domin.vo.CommentVO;
import com.ShiXi.comment.entity.Comment;
import com.ShiXi.comment.service.CommentService;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.BlogLikeMapper;
import com.ShiXi.common.mapper.CommentMapper;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.common.utils.UserHolder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    private final static String COMMENT_IMAGE = "comment_image/";
    private final static String BLOG_PREFIX = "blog/";
    private final static String COMMENT_PREFIX = "comment/";
    private final static String USER_PREFIX = "user/";


    @Resource
    private OSSUploadService ossUploadService;
    @Resource
    private BlogService blogService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result postComment(String content, Long blogId, MultipartFile image) {
        Long userId = UserHolder.getUser().getId();
        Long count = blogService.lambdaQuery().eq(Blog::getId, blogId).count();
        if(count == 0){
            return Result.fail("帖子不存在");
        }
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUserId(userId);
        comment.setBlogId(blogId);
        boolean success = save(comment);
        if (!success) {
            return Result.ok("评论保存失败");
        }
        Long commentId = comment.getId();
        String imageUrl=null;
        try {
            if (image != null) {
                String commentImageOssDir = COMMENT_IMAGE + BLOG_PREFIX + blogId + "/" + COMMENT_PREFIX + commentId + "/" + USER_PREFIX + userId;
                imageUrl = ossUploadService.uploadPicture(image, commentImageOssDir);
                comment.setImage(imageUrl);
            }
            return Result.ok(commentId);
        } catch (Exception e) {
            ossUploadService.deletePicture(imageUrl);
            throw new RuntimeException("图片上传失败");
        }
    }

    @Override
    public Result deleteComment(Long commentId) {
        Comment comment = lambdaQuery().eq(Comment::getId, commentId).one();
        String image = comment.getImage();
        if (image != null) {
            ossUploadService.deletePicture(image);
        }
        //TODO 点赞记录 回复评论记录
        removeById(commentId);
        return Result.ok();
    }

    @Override
    public Result getCommentById(Long commentId) {
        Comment comment = lambdaQuery().eq(Comment::getId, commentId).one();
        CommentVO commentVO = new CommentVO();
        BeanUtil.copyProperties(comment, commentVO);
        return Result.ok(commentVO);
    }

    @Override
    public Result pageQueryCommentByBlog(pageQueryBlogCommentReqDTO reqDTO) {

        Page<Comment> page = new Page<>(
                reqDTO.getPageNum(),  // 当前页码
                reqDTO.getPageSize() // 每页条数
        );

        // 2. 构建查询条件 + 排序
        Page<Comment> commentPage = lambdaQuery()
                .eq(Comment::getBlogId, reqDTO.getBlogId()) // 筛选目标博客的评论
                .orderByDesc(Comment::getLikeCount)         // 第一排序：点赞数从高到低
                .orderByDesc(Comment::getUpdateTime)        // 第二排序：更新时间从新到旧
                .page(page); // 执行分页查询
        return Result.ok(commentPage);
    }
}
