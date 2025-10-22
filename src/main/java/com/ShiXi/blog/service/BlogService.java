package com.ShiXi.blog.service;

import com.ShiXi.blog.ScheduledJob.ScoreCalculator;
import com.ShiXi.blog.ScheduledJob.domin.dto.BlogScoreDTO;
import com.ShiXi.blog.ScheduledJob.domin.dto.BlogScoreUpdateDTO;
import com.ShiXi.blog.domin.dto.HotBlogPageQueryReqDTO;
import com.ShiXi.blog.domin.dto.MyBlogListPageQueryReqDTO;
import com.ShiXi.blog.domin.dto.UserBlogListPageQueryReqDTO;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.blog.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface BlogService extends IService<Blog> {

    Result publishBlog(String title, String content,MultipartFile cover, List<MultipartFile> images);

    Result pageQueryMyBlog(MyBlogListPageQueryReqDTO reqDTO);

    Result queryBlogById(Long id);

    Result queryBlogListByUserId(UserBlogListPageQueryReqDTO reqDTO);

    Result deleteBlog(Long id);

    Result queryHotBlogs(HotBlogPageQueryReqDTO reqDTO);

    /**
     * 统计1年内的博客数量
     */
    int countByCreateTimeAfter(LocalDateTime oneYearAgo);

    /**
     * 分批查询博客核心字段（用于计算评分）
     * @param oneYearAgo 时间筛选条件
     * @param offset 偏移量
     * @param batchSize 批次大小
     * @return 博客核心信息列表
     */
    List<BlogScoreDTO> listBatchForScore(LocalDateTime oneYearAgo, int offset, int batchSize);

    /**
     * 批量更新博客评分
     * @param updateList 包含ID和评分的列表
     * @return 更新成功的条数
     */
    int batchUpdateScore(List<BlogScoreUpdateDTO> updateList);

    /**
     * 查询评分最高的前N条博客ID
     * @param limit 数量限制
     * @return 博客ID列表（按评分降序）
     */
    List<Long> listTopBlogIdsByScore(int limit);
}
