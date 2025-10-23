package com.ShiXi.blog.ScheduledJob;

import cn.hutool.json.JSONUtil;
import com.ShiXi.blog.ScheduledJob.domin.dto.BlogScoreDTO;
import com.ShiXi.blog.ScheduledJob.domin.dto.BlogScoreUpdateDTO;
import com.ShiXi.blog.service.BlogService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ScoreCalculator {
    @Resource
    private BlogService blogService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    // 分批大小
    private static final int BATCH_SIZE = 500;
    // 时间范围：1年内
    private static final int TIME_RANGE_DAYS = 365;
    // 热度权重
    private static final double HOT_WEIGHT = 0.4;
    // 时间权重
    private static final double TIME_WEIGHT = 0.6;
    // 时间衰减系数
    private static final double DECAY_FACTOR = 0.05;

    // Redis缓存键（存储评分最高的前N条博客ID，可根据需求调整N）
    private static final String REDIS_TOP_KEY = "blog:recommend:top:ids";
    // 缓存有效期（24小时）
    private static final long REDIS_EXPIRE_HOURS = 24;
    @XxlJob("ScoreCulScheduledJob")
    public void ScoreCulJobHandler() throws Exception {
        log.info("博客评分计算任务开始执行");
        LocalDateTime now = LocalDateTime.now();
        // 计算1年前的时间（筛选条件）
        LocalDateTime oneYearAgo = now.minusDays(TIME_RANGE_DAYS);

        try {
            // 1. 获取符合条件的总记录数
            int totalCount = blogService.countByCreateTimeAfter(oneYearAgo);
            log.info("符合条件的博客总数：{}条，将按{}条/批处理", totalCount, BATCH_SIZE);

            if (totalCount <= 0) {
                log.info("无符合条件的博客，任务结束");
                return;
            }

            // 2. 计算总批次数
            int totalBatches = (totalCount + BATCH_SIZE - 1) / BATCH_SIZE;
            List<Long> allBlogIds = new ArrayList<>(totalCount);

            // 3. 分批处理
            for (int batch = 0; batch < totalBatches; batch++) {
                int offset = batch * BATCH_SIZE;
                log.info("开始处理第{}批（偏移量：{}）", batch + 1, offset);

                // 3.1 分批查询博客核心字段（id, likes, create_time）
                List<BlogScoreDTO> blogList = blogService.listBatchForScore(oneYearAgo, offset, BATCH_SIZE);
                if (CollectionUtils.isEmpty(blogList)) {
                    log.info("第{}批无数据，跳过", batch + 1);
                    continue;
                }

                // 3.2 计算得分并收集ID
                List<BlogScoreUpdateDTO> updateList = new ArrayList<>(BATCH_SIZE);
                for (BlogScoreDTO blog : blogList) {
                    double score = calculateScore(blog, now);
                    updateList.add(new BlogScoreUpdateDTO(blog.getId(), score));
                    allBlogIds.add(blog.getId());
                }

                // 3.3 批量回写数据库
                int updateRows = blogService.batchUpdateScore(updateList);
                log.info("第{}批处理完成，更新条数：{}", batch + 1, updateRows);
            }

            // 4. 缓存处理（可选：只缓存评分最高的前N条ID，需查询数据库排序后缓存）
            if (!allBlogIds.isEmpty()) {
                // 查询评分最高的前200条ID（可根据业务调整数量）
                List<Long> topBlogIds = blogService.listTopBlogIdsByScore(200);
                stringRedisTemplate.opsForValue().set(
                        REDIS_TOP_KEY,
                        JSONUtil.toJsonStr(topBlogIds),
                        REDIS_EXPIRE_HOURS,
                        TimeUnit.HOURS
                );
                log.info("已缓存Top{}条博客ID到Redis", topBlogIds.size());
            }

            log.info("博客评分计算任务执行完成，总处理条数：{}", totalCount);

        } catch (Exception e) {
            log.error("博客评分计算任务执行失败", e);
            throw e; // 抛出异常让XXL-Job感知失败
        }
    }

    /**
     * 计算博客综合得分
     */
    private double calculateScore(BlogScoreDTO blog, LocalDateTime now) {
        // 计算时间差（天数）
        long days = ChronoUnit.DAYS.between(blog.getCreateTime(), now);
        // 时间衰减因子（exp(-k×天数)）
        double timeFactor = Math.exp(-DECAY_FACTOR * days);

        // 热度得分（log(likes + 1)）
        int likes = blog.getLikes() == null ? 0 : blog.getLikes();
        double hotScore = Math.log(likes + 1);

        // 综合得分
        return hotScore * HOT_WEIGHT + timeFactor * TIME_WEIGHT;
    }



}
