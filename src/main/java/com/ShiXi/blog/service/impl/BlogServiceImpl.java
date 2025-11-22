package com.ShiXi.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ShiXi.blog.ScheduledJob.ScoreCalculator;
import com.ShiXi.blog.ScheduledJob.domin.dto.BlogScoreDTO;
import com.ShiXi.blog.ScheduledJob.domin.dto.BlogScoreUpdateDTO;
import com.ShiXi.blog.domin.dto.HotBlogPageQueryReqDTO;
import com.ShiXi.blog.domin.dto.MyBlogListPageQueryReqDTO;
import com.ShiXi.blog.domin.dto.UserBlogListPageQueryReqDTO;
import com.ShiXi.blog.domin.vo.BlogVO;
import com.ShiXi.blog.domin.vo.MyBlogListVO;
import com.ShiXi.blog.entity.BlogLike;
import com.ShiXi.blog.service.BlogLikeService;
import com.ShiXi.comment.entity.Comment;
import com.ShiXi.comment.entity.CommentReply;
import com.ShiXi.comment.service.CommentLikeService;
import com.ShiXi.comment.service.CommentReplyService;
import com.ShiXi.comment.service.CommentService;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.blog.entity.Blog;
import com.ShiXi.common.mapper.BlogMapper;
import com.ShiXi.blog.service.BlogService;
import com.ShiXi.follow.service.FollowService;
import com.ShiXi.user.common.service.UserService;
import com.ShiXi.common.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private CommentLikeService commentLikeService;
    @Resource
    private OSSUploadService ossUploadService;
    @Resource
    private BlogLikeService blogLikeService;
    @Resource
    private CommentReplyService commentReplyService;
    @Resource
    private CommentService commentService;
    private static final String BLOG_IMAGE = "blog_image/";
    private static final String BLOG_PREFIX = "blog_id:";
    private static final String COVER_DIR = "cover_image/"; // 封面图子目录
    private static final String CONTENT_DIR = "content_images/"; // 内容图子目录
    private static final String USER_DIR = "user_id:";

    // Redis缓存的Top博客ID键（与定时任务中一致）
    private static final String REDIS_TOP_KEY = "blog:recommend:top:ids";
    // 缓存中推荐博客的默认最大数量（需与定时任务中缓存的数量一致）
    private static final int CACHE_MAX_SIZE = 200;

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
    @Transactional
    public Result deleteBlog(Long id) {
        //TODO 关联评论和点赞都要删除
        Blog blog = lambdaQuery().eq(Blog::getId, id).one();
        Long userId = UserHolder.getUser().getId();
        if(!blog.getUserId().equals(userId)) {
            return Result.fail("无法删除他人的帖子");
        }
        //删除对应点赞记录
        blogLikeService.lambdaUpdate().eq(BlogLike::getBlogId, id).remove();
        //删除所有对应的一级评论
        List<Long> commentIds = commentService.lambdaQuery()
                .eq(Comment::getBlogId, id)
                .select(Comment::getId).list()
                .stream()
                .map(Comment::getId)         // 提取每个评论的ID
                .toList(); // 收集为List<Long>

        //删除所有二级评论（基于一级评论ID关联）
        if (!commentIds.isEmpty()) {
            commentReplyService.lambdaUpdate()
                    .in(CommentReply::getCommentId, commentIds)
                    .remove();

            // 4. 删除所有一级评论（使用查询到的评论ID列表）
            commentService.removeByIds(commentIds);
        }
        removeById(id);
        return Result.ok();
    }

    @Override
    public Result queryHotBlogs(HotBlogPageQueryReqDTO reqDTO) {
        // 1. 计算分页参数
        int current = reqDTO.getPageNum(); // 页码（从1开始）
        int pageSize = reqDTO.getPageSize();
        int start = (current - 1) * pageSize; // 起始索引（0-based）
        int end = start + pageSize - 1;       // 结束索引
        // 2. 尝试从Redis缓存获取Top博客ID列表
        List<Long> topBlogIds = getTopBlogIdsFromCache();
        // 3. 处理缓存数据
        if (!CollectionUtils.isEmpty(topBlogIds)) {
            // 3.1 计算缓存中可提供的数据范围
            int cacheSize = topBlogIds.size();
            boolean isCacheEnough = end < cacheSize;

            if (isCacheEnough) {
                // 缓存足够：直接截取当前页的ID
                List<Long> currentPageIds = topBlogIds.subList(start, end + 1);
                List<Blog> blogs = listByIds(currentPageIds);
                List<BlogVO> blogVOs=new ArrayList<>();
                BeanUtils.copyProperties(blogs, blogVOs);
                return Result.ok(blogVOs);
            } else {
                // 缓存不足：先取缓存中剩余的部分
                List<Long> cachePartIds = topBlogIds.subList(start, cacheSize);

                List<Blog> cachePartBlogs = listByIds(cachePartIds);
                List<BlogVO> cachePartVOs = convertToVO(cachePartBlogs);
                int cachePartSize = cachePartVOs.size();

                // 3.2 计算还需要从数据库补充的数量
                int needMore = pageSize - cachePartSize;
                if (needMore > 0) {
                    // 从数据库查询补充数据（按score降序，排除已在缓存中的ID）
                    List<BlogVO> dbPartVOs = getMoreFromDb(needMore, topBlogIds);
                    cachePartVOs.addAll(dbPartVOs);
                }
                return Result.ok(cachePartVOs);
            }
        }

        // 4. 缓存未命中：直接从数据库查询
        log.warn("推荐缓存未命中，直接查询数据库");
        List<BlogVO> BlogVOs = pageRecommendFromDb(new Page<>(current, pageSize));
        return Result.ok(BlogVOs);
    }


    public List<BlogVO> convertToVO(List<Blog> blogs) {
        return blogs.stream().map(blog -> {
            BlogVO vo = new BlogVO();
            vo.setId(blog.getId());
            vo.setTitle(blog.getTitle());
            vo.setCover(blog.getCover());
            vo.setLikes(blog.getLikes());
            vo.setComments(blog.getComments());
            vo.setCreateTime(blog.getCreateTime());
            vo.setUserId(blog.getUserId());
            // 按需添加其他前端需要的字段（如摘要、图片数量等）
            return vo;
        }).collect(Collectors.toList());
    }


    public List<BlogVO> pageRecommendFromDb(Page<Blog> page) {

        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(Blog::getCreateTime, LocalDateTime.now().minusDays(365))
                .orderByDesc(Blog::getScore);
        Page<Blog> blogPage = page(page, queryWrapper);
        return blogPage.getRecords().stream()
                .map(blog -> {
                    BlogVO blogVO = new BlogVO();
                    BeanUtils.copyProperties(blog, blogVO);
                    return blogVO;
                })
                .toList();
    }
    public long countRecommendBeyondCache(List<Long> cacheIds) {
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(Blog::getCreateTime, LocalDateTime.now().minusDays(365))
                .notIn(!CollectionUtils.isEmpty(cacheIds), Blog::getId, cacheIds);
        return count(queryWrapper);
    }
    /**
     * 从数据库查询补充数据（排除已在缓存中的ID）
     */
    private List<BlogVO> getMoreFromDb(int needMore, List<Long> excludeIds) {
        // 查询条件：1年内的博客，按score降序，排除缓存中已有的ID
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(Blog::getCreateTime, LocalDateTime.now().minusDays(365))
                .notIn(Blog::getId, excludeIds) // 排除缓存中已返回的ID
                .orderByDesc(Blog::getScore)
                .last("LIMIT " + needMore);

        List<Blog> blogs = list(queryWrapper);
        return convertToVO(blogs);
    }


    private List<Long> getTopBlogIdsFromCache() {
        String cacheValue = stringRedisTemplate.opsForValue().get(REDIS_TOP_KEY);
        if (StrUtil.isBlank(cacheValue)) {
            return Collections.emptyList();
        }

        return JSONUtil.toList(cacheValue, Long.class);
    }

    /**
     * 统计1年内的博客数量（使用Lambda条件）
     */
    @Override
    public int countByCreateTimeAfter(LocalDateTime oneYearAgo) {
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(Blog::getCreateTime, oneYearAgo); // 条件：create_time >= oneYearAgo
        return (int) count(queryWrapper);
    }

    /**
     * 分批查询博客核心字段（使用Lambda+分页）
     */
    @Override
    public List<BlogScoreDTO> listBatchForScore(LocalDateTime oneYearAgo, int offset, int batchSize) {
        // 构建分页条件（offset = 页码-1 * batchSize，这里直接用offset作为起始位置）
        Page<Blog> page = new Page<>(offset / batchSize + 1, batchSize); // 页码从1开始

        // 构建查询条件：只查1年内的，且只返回id、likes、create_time字段
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(Blog::getCreateTime, oneYearAgo)
                .select(Blog::getId, Blog::getLikes, Blog::getCreateTime); // 只查询需要的字段

        // 分页查询
        IPage<Blog> blogPage = baseMapper.selectPage(page, queryWrapper);

        // 转换为DTO（只保留需要的字段）
        return blogPage.getRecords().stream()
                .map(blog -> {
                    BlogScoreDTO dto = new BlogScoreDTO();
                    dto.setId(blog.getId());
                    dto.setLikes(blog.getLikes());
                    dto.setCreateTime(blog.getCreateTime());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * 批量更新博客评分（使用MyBatis-Plus的批量更新方法）
     */
    @Override
    public int batchUpdateScore(List<BlogScoreUpdateDTO> updateList) {
        // 转换为Blog实体（只设置id和score）
        List<Blog> blogs = updateList.stream()
                .map(dto -> {
                    Blog blog = new Blog();
                    blog.setId(dto.getId());
                    blog.setScore(dto.getScore());
                    return blog;
                })
                .collect(Collectors.toList());

        // 批量更新（依赖MyBatis-Plus的updateBatchById方法，需导入批量更新插件）
        boolean success = updateBatchById(blogs);
        return success ? blogs.size() : 0;
    }

    /**
     * 查询评分最高的前N条博客ID（使用Lambda排序）
     */
    @Override
    public List<Long> listTopBlogIdsByScore(int limit) {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusDays(365);

        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(Blog::getCreateTime, oneYearAgo)
                .orderByDesc(Blog::getScore) // 按score降序
                .select(Blog::getId) // 只查id字段
                .last("LIMIT " + limit); // 限制条数

        return baseMapper.selectList(queryWrapper).stream()
                .map(Blog::getId)
                .collect(Collectors.toList());
    }
}
