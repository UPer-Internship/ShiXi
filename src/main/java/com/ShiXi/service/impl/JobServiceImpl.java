package com.ShiXi.service.impl;

import cn.hutool.json.JSONUtil;
import com.ShiXi.dto.JobFuzzyQueryDTO;
import com.ShiXi.dto.JobPageQueryDTO;
import com.ShiXi.dto.PageResult;
import com.ShiXi.dto.Result;
import com.ShiXi.entity.Blog;
import com.ShiXi.entity.Job;
import com.ShiXi.entity.User;
import com.ShiXi.mapper.JobMapper;
import com.ShiXi.mapper.UserMapper;
import com.ShiXi.service.JobService;
import com.ShiXi.utils.SystemConstants;
import com.ShiXi.utils.UserHolder;
import com.ShiXi.vo.InboxVO;
import com.ShiXi.vo.OnlineResumeVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {
    @Resource
    private JobMapper jobMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    OnlineResumeServiceImpl onlineResumeService;

    /**
     * 分页模糊匹配
     *
     * @param jobPageQueryDTO
     * @return
     */
    @Override
    public Result pageQuery(JobPageQueryDTO jobPageQueryDTO) {
        //log.info("分页查询职位信息{}", jobPageQueryDTO);
        // 创建分页对象
        Page<Job> jobPage = new Page<>(jobPageQueryDTO.getPage(), jobPageQueryDTO.getPageSize());

        // 创建查询条件并使用 QueryWrapper
        QueryWrapper<Job> queryWrapper = new QueryWrapper<>();

        // 判断type字段是否为空，如果不为空则添加查询条件
        if (jobPageQueryDTO.getType() != null) {
            queryWrapper.eq("type", jobPageQueryDTO.getType());
        }

        // 判断category字段是否为空，如果不为空则添加模糊查询条件
        if (jobPageQueryDTO.getCategory() != null) {
            queryWrapper.like("category", jobPageQueryDTO.getCategory());
        }

        // 执行分页查询
        jobMapper.selectPage(jobPage, queryWrapper);

        // 封装并返回分页结果
        return Result.ok(new PageResult(jobPage.getTotal(), jobPage.getRecords()));

//        Page<Job> jobPage=query().page(new Page<>(1,3));
//        List<Job> records = jobPage.getRecords();
//        // 查询用户
//
//        return Result.ok(records);

    }

    /**
     * 根据id查询职位
     *
     * @param id id
     * @return Job信息
     */
    @Override
    public Result queryById(Long id) {
        return Result.ok(getById(id));
    }

    @Override
    public Result deliverResume(Long id) {//传入的是job的id
        //获取job对象
        //在job对象中找到发布者的id
        Long HRId = getById(id).getPublisherId();
        //找出学生的id
        Long userId = UserHolder.getUser().getId();

        //投递到发布者的收件箱中
        String key = "inbox:" + HRId;//hr的收件箱
        InboxVO inboxVO = new InboxVO();
        inboxVO.setJobId(id);
        inboxVO.setSubmitterId(userId);
//        inboxVO.setIcon("");
//        inboxVO.setGender("");
//        inboxVO.setJobName("");
//        inboxVO.s
        String Json = JSONUtil.toJsonStr(inboxVO);
        stringRedisTemplate.opsForZSet().add(key, Json, System.currentTimeMillis());
        //TODO 异步回写mysql
        return Result.ok();
    }

    /**
     * 到数据库中模糊查询
     *
     * @param jobFuzzyQueryDTO 查询条件
     * @return 查询结果
     */
    @Override
    public Result fuzzyQuery(JobFuzzyQueryDTO jobFuzzyQueryDTO) {
        log.info("模糊查询职位信息{}", jobFuzzyQueryDTO);
        // 创建分页查询信息
        Page<Job> jobPage = new Page<>(jobFuzzyQueryDTO.getPage(), jobFuzzyQueryDTO.getPageSize());

        // 创建查询条件并使用 QueryWrapper
        QueryWrapper<Job> queryWrapper = new QueryWrapper<>();

        // 添加模糊查询条件
        if (jobFuzzyQueryDTO.getKeyWord() != null && !jobFuzzyQueryDTO.getKeyWord().isEmpty()) {
            queryWrapper.like("title", jobFuzzyQueryDTO.getKeyWord()) // 职位名
                    .or()
                    .like("category", jobFuzzyQueryDTO.getKeyWord()) // 类别
                    .or()
                    .like("type", jobFuzzyQueryDTO.getKeyWord()) // 类型
                    .or()
                    .like("detailed_information", jobFuzzyQueryDTO.getKeyWord()) // 职位描述
                    .or()
                    .like("work_location", jobFuzzyQueryDTO.getKeyWord()) // 工作地点
                    .or()
                    .like("enterprise_name", jobFuzzyQueryDTO.getKeyWord()) // 公司名
                    .or()
                    .like("enterprise_type", jobFuzzyQueryDTO.getKeyWord()) // 公司类型
                    .or()
                    .like("enterprise_scale", jobFuzzyQueryDTO.getKeyWord()) // 公司规模
                    .or()
                    .like("frequency", jobFuzzyQueryDTO.getKeyWord()) // 工作频率
                    .or()
                    .like("salary", jobFuzzyQueryDTO.getKeyWord()); // 薪资
        }

        // 执行分页查询
        jobMapper.selectPage(jobPage, queryWrapper);

        // 返回查询结果
        return Result.ok(new PageResult(jobPage.getTotal(), jobPage.getRecords()));
    }


}
