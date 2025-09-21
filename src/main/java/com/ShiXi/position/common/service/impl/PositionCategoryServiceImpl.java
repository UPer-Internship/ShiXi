package com.ShiXi.position.common.service.impl;

import cn.hutool.json.JSONUtil;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.utils.RedissonLockUtil;
import com.ShiXi.position.common.domin.vo.JobCategoryVO;
import com.ShiXi.position.common.entity.JobCategory;
import com.ShiXi.position.common.service.PositionCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.ShiXi.common.utils.RedisConstants.REBUILD_JOB_CATEGORY_BUFFER_LOCK;

@Slf4j
@Service
public class PositionCategoryServiceImpl extends ServiceImpl<BaseMapper<JobCategory>, JobCategory> implements PositionCategoryService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    @Resource
    private RedissonLockUtil redissonLockUtil;

    @Override
    public Result getJobCategoryList() {
        String key = "jobCategoryList:";
        String jobCategoryListJson = stringRedisTemplate.opsForValue().get(key);
        if (jobCategoryListJson != null) {
            // 缓存命中
            return Result.ok(JSONUtil.toBean(jobCategoryListJson, JobCategoryVO.class));
        }
        
        boolean isLock = redissonLockUtil.tryLock(REBUILD_JOB_CATEGORY_BUFFER_LOCK, 3, 10, TimeUnit.SECONDS);
        if (!isLock) {
            // 拿锁失败
            return Result.fail("请稍后再试");
        }
        
        try {
            // 查询所有岗位类别
            List<JobCategory> jobCategoryList = this.list();
            
            if (jobCategoryList == null || jobCategoryList.isEmpty()) {
                JobCategoryVO emptyVO = new JobCategoryVO();
                // 缓存空结果
                stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(emptyVO));
                return Result.ok(emptyVO);
            }

            JobCategoryVO jobCategoryVO = new JobCategoryVO();

            // 按一级分类标签分组
            Map<String, List<JobCategory>> categoriesMap = jobCategoryList.stream()
                    .collect(Collectors.groupingBy(JobCategory::getFirstLevelCategoryLabel));

            List<JobCategoryVO.FirstLevelCategoryLabel> firstLevelCategoryLabelList = new ArrayList<>();

            for (Map.Entry<String, List<JobCategory>> entry : categoriesMap.entrySet()) {
                JobCategoryVO.FirstLevelCategoryLabel firstLevelCategoryLabel = new JobCategoryVO.FirstLevelCategoryLabel();
                firstLevelCategoryLabel.setFirstLevelCategoryLabel(entry.getKey());

                // 转换为Category列表
                List<JobCategoryVO.Category> categoryList = entry.getValue().stream()
                        .map(jobCategory -> {
                            JobCategoryVO.Category category = new JobCategoryVO.Category();
                            category.setId(jobCategory.getId());
                            category.setCategory(jobCategory.getCategory());
                            category.setDescription(jobCategory.getDescription());
                            return category;
                        })
                        .collect(Collectors.toList());

                firstLevelCategoryLabel.setCategory(categoryList);
                firstLevelCategoryLabelList.add(firstLevelCategoryLabel);
            }

            jobCategoryVO.setCategories(firstLevelCategoryLabelList);
            
            // 缓存结果
            stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(jobCategoryVO));
            
            return Result.ok(jobCategoryVO);

        } catch (Exception e) {
            log.error("获取岗位类别列表失败", e);
            return Result.fail("获取岗位类别列表失败");
        } finally {
            // 释放锁
            redissonLockUtil.unlock(REBUILD_JOB_CATEGORY_BUFFER_LOCK);
        }
    }
}