package com.ShiXi.blog.service.impl;

import cn.hutool.json.JSONUtil;
import com.ShiXi.blog.domin.dto.MyBlogListPageQueryReqDTO;
import com.ShiXi.blog.domin.dto.UserBlogListPageQueryReqDTO;
import com.ShiXi.blog.domin.vo.BlogVO;
import com.ShiXi.blog.domin.vo.MyBlogListVO;
import com.ShiXi.blog.service.BlogLikeService;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.blog.entity.Blog;
import com.ShiXi.common.mapper.BlogMapper;
import com.ShiXi.blog.service.BlogService;
import com.ShiXi.follow.service.FollowService;
import com.ShiXi.user.common.service.UserService;
import com.ShiXi.common.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service

public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    @Resource
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private FollowService followService;

    @Resource
    private OSSUploadService ossUploadService;
    @Resource
    private BlogLikeService blogLikeService;

    private static final String BLOG_IMAGE = "blog_image/";
    private static final String BLOG_PREFIX = "blog_id:";
    private static final String COVER_DIR = "cover_image/"; // 封面图子目录
    private static final String CONTENT_DIR = "content_images/"; // 内容图子目录
    private static final String USER_DIR = "user_id:";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result publishBlog(String title, String content,MultipartFile cover, List<MultipartFile> images) {
        // 1. 获取当前用户ID
        Long userId  = UserHolder.getUser().getId();

        // 2. 保存博客基本信息（生成自增ID）
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setUserId(userId);
        save(blog); // 保存后自动回填ID

        // 3. 获取自增博客ID
        Long blogId = blog.getId();
        if (blogId == null) {
            throw new RuntimeException("博客ID生成失败");
        }

        // 初始化OSS路径（按用户+博客ID分层）
        String baseOssDir = BLOG_IMAGE +BLOG_PREFIX+ blogId+ "/";
        String coverOssDir = baseOssDir + COVER_DIR; // 封面图路径
        String contentOssDir = baseOssDir + CONTENT_DIR; // 内容图路径

        // 记录已上传的图片（用于异常回滚）
        List<String> forRollBackUrls = new ArrayList<>();
        String coverUrl = null; // 封面图URL

        try {
            // 4. 处理封面图上传（单独目录存储）
            if (cover != null && !cover.isEmpty()) {
                coverUrl = ossUploadService.uploadPicture(cover, coverOssDir);
                forRollBackUrls.add(coverUrl); // 记录封面图
                blog.setCover(coverUrl);
            }

            // 5. 处理内容图片上传
            List<String> contentImageUrls = new ArrayList<>();
            if (images != null && !images.isEmpty()) {
                for (MultipartFile image : images) {
                    if (image.isEmpty()) {
                        continue;
                    }
                    String url = ossUploadService.uploadPicture(image, contentOssDir);
                    contentImageUrls.add(url);
                    forRollBackUrls.add(url); // 记录内容图
                }
            }

            // 6. 更新博客的图片信息
            blog.setImages(JSONUtil.toJsonStr(contentImageUrls)); // 内容图列表
            updateById(blog);

            // 7. 返回结果（携带博客ID）
            return Result.ok(blogId);
        } catch (Exception e) {
            // 异常时删除已上传的所有图片（封面+内容）
            for (String url : forRollBackUrls) {
                ossUploadService.deletePicture(url);
            }
            throw new RuntimeException("发布博客失败：" + e.getMessage());
        }
    }

    @Override
    public Result pageQueryMyBlog(MyBlogListPageQueryReqDTO reqDTO) {
        Long userId = UserHolder.getUser().getId();
        Page<Blog> page = new Page<>(reqDTO.getPageNum(), reqDTO.getPageSize());

        // 3. 构建查询条件（where user_id = ?）
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Blog::getUserId, userId)  // 只查当前用户的博客
                .orderByDesc(Blog::getCreateTime); // 按创建时间倒序（最新的在前）

        queryWrapper.select(
                Blog::getId,
                Blog::getTitle,
                Blog::getContent,
                Blog::getCover,
                Blog::getCreateTime  // 如需创建时间则保留
        );
        // 4. 执行SQL分页查询
        Page<Blog> blogPage = page(page, queryWrapper);

        // 4. 转换为VO（只保留需要的字段）
        List<MyBlogListVO> voList = blogPage.getRecords().stream()
                .map(blog -> {
                    MyBlogListVO vo = new MyBlogListVO();
                    // 拷贝指定字段（id、title、content、coverUrl、createTime）
                    BeanUtils.copyProperties(blog, vo);
                    return vo;
                })
                .toList();
        Page<MyBlogListVO> resultPage = new Page<>();
        resultPage.setRecords(voList);
        // 5. 封装结果返回（包含总条数、总页数、当前页数据等）
        return Result.ok(voList);
    }

    @Override
    public Result queryBlogById(Long id) {
        Blog blog =lambdaQuery().eq(Blog::getId, id).one();
        BlogVO blogVO = new BlogVO();
        BeanUtils.copyProperties(blog, blogVO);
        blogVO.setImages(JSONUtil.toList(blog.getImages(),String.class));
        return Result.ok(blogVO);
    }

    @Override
    public Result queryBlogListByUserId(UserBlogListPageQueryReqDTO reqDTO) {
        Long userId = reqDTO.getUserId();
        Page<Blog> page = new Page<>(reqDTO.getPageNum(), reqDTO.getPageSize());

        // 3. 构建查询条件（where user_id = ?）
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Blog::getUserId, userId)  // 只查当前用户的博客
                .orderByDesc(Blog::getCreateTime); // 按创建时间倒序（最新的在前）

        queryWrapper.select(
                Blog::getId,
                Blog::getTitle,
                Blog::getContent,
                Blog::getCover,
                Blog::getCreateTime  // 如需创建时间则保留
        );
        // 4. 执行SQL分页查询
        Page<Blog> blogPage = page(page, queryWrapper);

        // 4. 转换为VO（只保留需要的字段）
        List<MyBlogListVO> voList = blogPage.getRecords().stream()
                .map(blog -> {
                    MyBlogListVO vo = new MyBlogListVO();
                    // 拷贝指定字段（id、title、content、coverUrl、createTime）
                    BeanUtils.copyProperties(blog, vo);
                    return vo;
                })
                .toList();
        Page<MyBlogListVO> resultPage = new Page<>();
        resultPage.setRecords(voList);
        // 5. 封装结果返回（包含总条数、总页数、当前页数据等）
        return Result.ok(voList);
    }

    @Override
    public Result deleteBlog(Long id) {
        //TODO 关联评论和点赞都要删除
        Blog blog = lambdaQuery().eq(Blog::getId, id).one();

        removeById(id);
        return Result.ok();
    }


}
