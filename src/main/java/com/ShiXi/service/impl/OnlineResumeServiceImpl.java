package com.ShiXi.service.impl;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.OnlineResume;
import com.ShiXi.entity.ResumeExperience;
import com.ShiXi.mapper.OnlineResumeMapper;
import com.ShiXi.mapper.ResumeExperienceMapper;
import com.ShiXi.service.OnlineResumeService;
import com.ShiXi.utils.UserHolder;
import com.ShiXi.vo.OnlineResumeVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class OnlineResumeServiceImpl implements OnlineResumeService {
    @Resource
    private OnlineResumeMapper onlineResumeMapper;
    @Resource
    private ResumeExperienceMapper resumeExperienceMapper;

    /**
     * 获取在线简历
     *
     * @return 在线简历的vo类
     */
    @Override
    public Result getOnlineResume() {
        //获取当前用户id
        Long id = UserHolder.getUser().getId();
        //根据当前用户id查出在线简历
        QueryWrapper<OnlineResume> queryForResume = new QueryWrapper<>();
        queryForResume.eq("user_id", id);
        OnlineResume onlineResume = onlineResumeMapper.selectOne(queryForResume);
        //根据在线简历id查出在线简历的简历经历
        QueryWrapper<ResumeExperience> queryForExperience = new QueryWrapper<>();
        queryForExperience.eq("online_resume_id", onlineResume.getId());
        List<ResumeExperience> resumeExperiences = resumeExperienceMapper.selectList(queryForExperience);
        //进行组装
        OnlineResumeVO onlineResumeVO = new OnlineResumeVO();
        BeanUtils.copyProperties(onlineResume, onlineResumeVO);
        for (ResumeExperience experience : resumeExperiences) {
            switch (experience.getType()) {
                case "实习":
                    onlineResumeVO.getInternshipExperiences().add(experience);
                    break;
                case "工作":
                    onlineResumeVO.getWorkExperiences().add(experience);
                    break;
                case "项目":
                    onlineResumeVO.getProjectExperiences().add(experience);
                    break;
                case "作品集":
                    onlineResumeVO.getPortfolioExperiences().add(experience);
                    break;
            }
        }
        return Result.ok(onlineResumeVO);
    }
}
