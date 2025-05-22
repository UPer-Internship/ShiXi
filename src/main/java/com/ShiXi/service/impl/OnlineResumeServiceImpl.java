package com.ShiXi.service.impl;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.ResumeExperience;
import com.ShiXi.entity.StudentInfo;
import com.ShiXi.mapper.ResumeExperienceMapper;
import com.ShiXi.mapper.StudentInfoMapper;
import com.ShiXi.service.OnlineResumeService;
import com.ShiXi.utils.UserHolder;
import com.ShiXi.vo.OnlineResumeVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class OnlineResumeServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements OnlineResumeService {
    @Resource
    private StudentInfoMapper studentInfoMapper;
    @Resource
    private ResumeExperienceMapper resumeExperienceMapper;

    /**
     * 保存在线简历
     *
     * @return 保存成功返回成功，否则返回失败
     */
    @Override
    public Result saveResumeInfo(StudentInfo studentInfo){
        //先查出当前user
        //Long userId = 1L;
        Long userId = UserHolder.getUser().getId();
        //更新id为userId的信息
        studentInfo.setId(userId);
        updateById(studentInfo);
        return Result.ok();
    }

    /**
     * 保存在线简历的经历信息
     *
     * @return 保存成功返回成功，否则返回失败
     */
    @Override
    public Result saveExperienceInfo(ResumeExperience resumeExperience){
        //Long userId = 1L;
        Long userId = UserHolder.getUser().getId();
        resumeExperience.setStudentInfoId(userId);
        resumeExperienceMapper.insert(resumeExperience);
        return Result.ok();
    }

    /**
     * 修改在线简历
     *
     * @return 修改成功返回成功，否则返回失败
     */
    @Override
    public Result changeResumeInfo(StudentInfo studentInfo){
        //Long userId = 1L;
        Long userId = UserHolder.getUser().getId();
        studentInfo.setId(userId);
        updateById(studentInfo);
        return Result.ok();
    }

    /**
     * 修改在线简历的经历信息
     *
     * @return 修改成功返回成功，否则返回失败
     */
    @Override
    public Result changeExperienceInfo(ResumeExperience resumeExperience){
        Long userId = UserHolder.getUser().getId();
        resumeExperience.setStudentInfoId(userId);
        resumeExperienceMapper.updateById(resumeExperience);
        return Result.ok();
    }

    /**
     * 获取在线简历
     *
     * @return 在线简历的vo类
     */
    @Override
    public Result getOnlineResume(){
        //  获取当前用户的ID
        //Long userId = 1L;
        Long userId = UserHolder.getUser().getId();

        //  查询 student_info 表中的在线简历基本信息
        QueryWrapper<StudentInfo> resumeQueryWrapper = new QueryWrapper<>();
        resumeQueryWrapper.eq("user_id", userId); // 根据 user_id 查询学生信息
        StudentInfo studentInfo = studentInfoMapper.selectOne(resumeQueryWrapper);

        if (studentInfo == null) {
            return Result.fail("未找到该用户的简历信息");
        }

        //  查询 resume_experience 表中该简历相关的经历信息
        QueryWrapper<ResumeExperience> experienceQueryWrapper = new QueryWrapper<>();
        experienceQueryWrapper.eq("student_info_id", studentInfo.getId());
        List<ResumeExperience> resumeExperiences = resumeExperienceMapper.selectList(experienceQueryWrapper);

        //  构建 VO 对象并赋值
        OnlineResumeVO onlineResumeVO = new OnlineResumeVO();
        BeanUtils.copyProperties(studentInfo,onlineResumeVO); // 属性拷贝

        //  将不同类型的经历分类放入对应的集合中
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
                default:
                    break;
            }
        }

        // 6. 返回结果给前端
        return Result.ok(onlineResumeVO);
    }


    public OnlineResumeVO queryResumeById(Long id) {


        //  查询 student_info 表中的在线简历基本信息
        QueryWrapper<StudentInfo> resumeQueryWrapper = new QueryWrapper<>();
        resumeQueryWrapper.eq("user_id", id); // 根据 user_id 查询学生信息
        StudentInfo studentInfo = studentInfoMapper.selectOne(resumeQueryWrapper);

//        if (studentInfo == null) {
//            return Result.fail("未找到该用户的简历信息");
//        }

        //  查询 resume_experience 表中该简历相关的经历信息
        QueryWrapper<ResumeExperience> experienceQueryWrapper = new QueryWrapper<>();
        experienceQueryWrapper.eq("student_info_id", studentInfo.getId());
        List<ResumeExperience> resumeExperiences = resumeExperienceMapper.selectList(experienceQueryWrapper);

        //  构建 VO 对象并赋值
        OnlineResumeVO onlineResumeVO = new OnlineResumeVO();
        BeanUtils.copyProperties(studentInfo,onlineResumeVO); // 属性拷贝

        //  将不同类型的经历分类放入对应的集合中
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
                default:
                    break;
            }
        }

        // 6. 返回结果给前端
        return onlineResumeVO;
    }
}
