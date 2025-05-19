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
        Page<Job> jobPage = query()
                .eq(jobPageQueryDTO.getType() != null, "type", jobPageQueryDTO.getType())
                .like(jobPageQueryDTO.getCategory() != null, "category", jobPageQueryDTO.getCategory())
                .page(new Page<>(jobPageQueryDTO.getPage(), jobPageQueryDTO.getPageSize()));

        List<Job> records = jobPage.getRecords();
        PageResult pageResult = new PageResult(records.size(), records);
        return Result.ok(pageResult);

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
        // 使用 query() 进行模糊查询
        Page<Job> jobPage = query()
                .like(jobFuzzyQueryDTO.getKeyWord() != null && !jobFuzzyQueryDTO.getKeyWord().isEmpty(), "title", jobFuzzyQueryDTO.getKeyWord()) // 职位名
                .or()
                .like(jobFuzzyQueryDTO.getKeyWord() != null && !jobFuzzyQueryDTO.getKeyWord().isEmpty(), "category", jobFuzzyQueryDTO.getKeyWord()) // 类别
                .or()
                .like(jobFuzzyQueryDTO.getKeyWord() != null && !jobFuzzyQueryDTO.getKeyWord().isEmpty(), "type", jobFuzzyQueryDTO.getKeyWord()) // 类型
                .or()
                .like(jobFuzzyQueryDTO.getKeyWord() != null && !jobFuzzyQueryDTO.getKeyWord().isEmpty(), "detailed_information", jobFuzzyQueryDTO.getKeyWord()) // 职位描述
                .or()
                .like(jobFuzzyQueryDTO.getKeyWord() != null && !jobFuzzyQueryDTO.getKeyWord().isEmpty(), "work_location", jobFuzzyQueryDTO.getKeyWord()) // 工作地点
                .or()
                .like(jobFuzzyQueryDTO.getKeyWord() != null && !jobFuzzyQueryDTO.getKeyWord().isEmpty(), "enterprise_name", jobFuzzyQueryDTO.getKeyWord()) // 公司名
                .or()
                .like(jobFuzzyQueryDTO.getKeyWord() != null && !jobFuzzyQueryDTO.getKeyWord().isEmpty(), "enterprise_type", jobFuzzyQueryDTO.getKeyWord()) // 公司类型
                .or()
                .like(jobFuzzyQueryDTO.getKeyWord() != null && !jobFuzzyQueryDTO.getKeyWord().isEmpty(), "enterprise_scale", jobFuzzyQueryDTO.getKeyWord()) // 公司规模
                .or()
                .like(jobFuzzyQueryDTO.getKeyWord() != null && !jobFuzzyQueryDTO.getKeyWord().isEmpty(), "frequency", jobFuzzyQueryDTO.getKeyWord()) // 工作频率
                .or()
                .like(jobFuzzyQueryDTO.getKeyWord() != null && !jobFuzzyQueryDTO.getKeyWord().isEmpty(), "salary", jobFuzzyQueryDTO.getKeyWord()) // 薪资
                .page(new Page<>(jobFuzzyQueryDTO.getPage(), jobFuzzyQueryDTO.getPageSize()));

        // 获取查询结果
        List<Job> records = jobPage.getRecords();
        PageResult pageResult = new PageResult(records.size(), records);

        // 返回查询结果
        return Result.ok(pageResult);
    }


}
