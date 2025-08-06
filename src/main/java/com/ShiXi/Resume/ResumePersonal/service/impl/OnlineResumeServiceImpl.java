package com.ShiXi.Resume.ResumePersonal.service.impl;

import cn.hutool.json.JSONUtil;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.Resume.ResumePersonal.domin.dto.UpdateResumeDTO;
import com.ShiXi.Resume.ResumePersonal.domin.vo.ResumeVO;
import com.ShiXi.Resume.ResumePersonal.entity.Resume;
import com.ShiXi.common.mapper.ResumeExperienceMapper;
import com.ShiXi.common.mapper.StudentInfoMapper;
import com.ShiXi.Resume.ResumePersonal.service.OnlineResumeService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.common.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class OnlineResumeServiceImpl extends ServiceImpl<ResumeExperienceMapper, Resume> implements OnlineResumeService {
    @Resource
    private StudentInfoMapper studentInfoMapper;
    @Resource
    private ResumeExperienceMapper resumeExperienceMapper;
    @Resource
    private OnlineResumeService onlineResumeService;
    @Resource
    private UserService userService;
//    /**
//     * 保存在线简历
//     *
//     * @return 保存成功返回成功，否则返回失败
//     */
//    @Override
//    public Result saveResumeInfo(StudentInfo studentInfo){
//        //先查出当前user
//        //Long userId = 1L;
//        Long userId = UserHolder.getUser().getId();
//        //更新id为userId的信息
//        studentInfo.setId(userId);
//        updateById(studentInfo);
//        return Result.ok();
//    }
//
//    /**
//     * 保存在线简历的经历信息
//     *
//     * @return 保存成功返回成功，否则返回失败
//     */
//    @Override
//    public Result saveExperienceInfo(ResumeExperience resumeExperience){
//        //Long userId = 1L;
//        Long userId = UserHolder.getUser().getId();
//        resumeExperience.setUserId(userId);
//        resumeExperienceMapper.insert(resumeExperience);
//        return Result.ok();
//    }
//
//    /**
//     * 修改在线简历
//     *
//     * @return 修改成功返回成功，否则返回失败
//     */
//    @Override
//    public Result changeResumeInfo(StudentInfo studentInfo){
//        //Long userId = 1L;
//        Long userId = UserHolder.getUser().getId();
//        studentInfo.setId(userId);
//        updateById(studentInfo);
//        return Result.ok();
//    }
//
//    /**
//     * 修改在线简历的经历信息
//     *
//     * @return 修改成功返回成功，否则返回失败
//     */
//    @Override
//    public Result changeExperienceInfo(ResumeExperience resumeExperience){
//        Long userId = UserHolder.getUser().getId();
//        resumeExperience.setUserId(userId);
//        resumeExperienceMapper.updateById(resumeExperience);
//        return Result.ok();
//    }
//
//    /**
//     * 获取在线简历
//     *
//     * @return 在线简历的vo类
//     */
//    @Override
//    public Result getOnlineResume(){
//        //  获取当前用户的ID
//        //Long userId = 1L;
//        Long userId = UserHolder.getUser().getId();
//
//        //  查询 student_info 表中的在线简历基本信息
//        QueryWrapper<StudentInfo> resumeQueryWrapper = new QueryWrapper<>();
//        resumeQueryWrapper.eq("user_id", userId); // 根据 user_id 查询学生信息
//        StudentInfo studentInfo = studentInfoMapper.selectOne(resumeQueryWrapper);
//
//        if (studentInfo == null) {
//            return Result.fail("未找到该用户的简历信息");
//        }
//
//        //  查询 resume_experience 表中该简历相关的经历信息
//        QueryWrapper<ResumeExperience> experienceQueryWrapper = new QueryWrapper<>();
//        experienceQueryWrapper.eq("student_info_id", studentInfo.getId());
//        List<ResumeExperience> resumeExperiences = resumeExperienceMapper.selectList(experienceQueryWrapper);
//
//        //  构建 VO 对象并赋值
//        OnlineResumeVO onlineResumeVO = new OnlineResumeVO();
//        BeanUtils.copyProperties(studentInfo,onlineResumeVO); // 属性拷贝
//
//        //  将不同类型的经历分类放入对应的集合中
//        for (ResumeExperience experience : resumeExperiences) {
//            switch (experience.getType()) {
//                case "实习":
//                    onlineResumeVO.getInternshipExperiences().add(experience);
//                    break;
//                case "工作":
//                    onlineResumeVO.getWorkExperiences().add(experience);
//                    break;
//                case "项目":
//                    onlineResumeVO.getProjectExperiences().add(experience);
//                    break;
//                case "作品集":
//                    onlineResumeVO.getPortfolioExperiences().add(experience);
//                    break;
//                default:
//                    break;
//            }
//        }
//
//        // 6. 返回结果给前端
//        return Result.ok(onlineResumeVO);
//    }
//
//
//    public OnlineResumeVO queryResumeById(Long id) {
//
//
//        //  查询 student_info 表中的在线简历基本信息
//        QueryWrapper<StudentInfo> resumeQueryWrapper = new QueryWrapper<>();
//        resumeQueryWrapper.eq("user_id", id); // 根据 user_id 查询学生信息
//        StudentInfo studentInfo = studentInfoMapper.selectOne(resumeQueryWrapper);
//
////        if (studentInfo == null) {
////            return Result.fail("未找到该用户的简历信息");
////        }
//
//        //  查询 resume_experience 表中该简历相关的经历信息
//        QueryWrapper<ResumeExperience> experienceQueryWrapper = new QueryWrapper<>();
//        experienceQueryWrapper.eq("student_info_id", studentInfo.getId());
//        List<ResumeExperience> resumeExperiences = resumeExperienceMapper.selectList(experienceQueryWrapper);
//
//        //  构建 VO 对象并赋值
//        OnlineResumeVO onlineResumeVO = new OnlineResumeVO();
//        BeanUtils.copyProperties(studentInfo,onlineResumeVO); // 属性拷贝
//
//        //  将不同类型的经历分类放入对应的集合中
//        for (ResumeExperience experience : resumeExperiences) {
//            switch (experience.getType()) {
//                case "实习":
//                    onlineResumeVO.getInternshipExperiences().add(experience);
//                    break;
//                case "工作":
//                    onlineResumeVO.getWorkExperiences().add(experience);
//                    break;
//                case "项目":
//                    onlineResumeVO.getProjectExperiences().add(experience);
//                    break;
//                case "作品集":
//                    onlineResumeVO.getPortfolioExperiences().add(experience);
//                    break;
//                default:
//                    break;
//            }
//        }
//
//        // 6. 返回结果给前端
//        return onlineResumeVO;
//    }


    @Override
    public Result getResumeByResumeId(Long resumeId){
        Resume resumeExperience = lambdaQuery()
                .eq(Resume::getId, resumeId)  // 直接引用实体类的userId字段
                .one();
        if(resumeExperience == null){
            return null;
        }
        ResumeVO resumeExperienceVO = new ResumeVO();
        resumeExperienceVO.setId(resumeExperience.getId())
                .setUserId(resumeExperience.getUserId())
                .setProjectExperiences(JSONUtil.toList(resumeExperience.getProjectExperiences(), ResumeVO.projectExperience.class))
                .setWorkAndInternshipExperiences(JSONUtil.toList(resumeExperience.getWorkAndInternshipExperiences(), ResumeVO.workAndInternshipExperience.class))
                .setEducationExperiences(JSONUtil.toList(resumeExperience.getEducationExperiences(),ResumeVO.educationExperience.class))
                .setGender(resumeExperience.getGender())
                .setBirthDate(resumeExperience.getBirthDate())
                .setName(resumeExperience.getName())
                .setPhone(resumeExperience.getPhone())
                .setWechat(resumeExperience.getWechat());
        return Result.ok(resumeExperienceVO);
    }

    @Override
    public Result getMyResume() {
        //获取用户id
        Long id = UserHolder.getUser().getId();
        //获取用户经历
        Resume resumeExperience = lambdaQuery()
                .eq(Resume::getUserId, id)  // 直接引用实体类的userId字段
                .one();
        if(resumeExperience==null){
            return Result.fail("此用户还没有上传简历");
        }
        //获取用户基本信息
        ResumeVO resumeExperienceVO = new ResumeVO();
        resumeExperienceVO.setId(resumeExperience.getId())
                .setUserId(resumeExperience.getUserId())
                .setProjectExperiences(JSONUtil.toList(resumeExperience.getProjectExperiences(), ResumeVO.projectExperience.class))
                .setWorkAndInternshipExperiences(JSONUtil.toList(resumeExperience.getWorkAndInternshipExperiences(), ResumeVO.workAndInternshipExperience.class))
                .setEducationExperiences(JSONUtil.toList(resumeExperience.getEducationExperiences(),ResumeVO.educationExperience.class))
                .setGender(resumeExperience.getGender())
                .setBirthDate(resumeExperience.getBirthDate())
                .setName(resumeExperience.getName())
                .setPhone(resumeExperience.getPhone())
                .setWechat(resumeExperience.getWechat());
        return Result.ok(resumeExperienceVO);
    }

    public Result updateMyExperience(UpdateResumeDTO reqDTO) {
        Long userId = UserHolder.getUser().getId();

        // 先查询该用户是否已有经验记录
        Resume experience = onlineResumeService.lambdaQuery()
                .eq(Resume::getUserId, userId)
                .one();

        if (experience != null) {
            // 有记录则更新
            boolean success = onlineResumeService.lambdaUpdate()
                    .eq(Resume::getUserId, userId)
                    .set(Resume::getWorkAndInternshipExperiences, JSONUtil.toJsonStr(reqDTO.getWorkAndInternshipExperiences()))
                    .set(Resume::getProjectExperiences, JSONUtil.toJsonStr(reqDTO.getProjectExperiences()))
                    .set(Resume::getResumeLink, reqDTO.getResumeLink())
                    .set(Resume::getEducationExperiences,JSONUtil.toJsonStr(reqDTO.getEducationExperiences()))
                    .set(Resume::getExpectedPosition,reqDTO.getExpectedPosition())
                    .set(Resume::getAdvantages,reqDTO.getAdvantages())
                    .set(Resume::getBirthDate,reqDTO.getBirthDate())
                    .set(Resume::getName,reqDTO.getName())
                    .set(Resume::getPhone,reqDTO.getPhone())
                    .set(Resume::getWechat,reqDTO.getWechat())
                    .set(Resume::getGender,reqDTO.getGender())

                    .update();

            if (success) {
                return Result.ok();
            }
            return Result.fail("更新失败");
        } else {
            // 无记录则新建
            Resume newExperience = new Resume();
            newExperience.setUserId(userId);
            newExperience.setWorkAndInternshipExperiences(JSONUtil.toJsonStr(reqDTO.getWorkAndInternshipExperiences()));
            newExperience.setProjectExperiences(JSONUtil.toJsonStr(reqDTO.getProjectExperiences()));
            newExperience.setResumeLink(reqDTO.getResumeLink());
            newExperience.setEducationExperiences(JSONUtil.toJsonStr(reqDTO.getEducationExperiences()));
            newExperience.setExpectedPosition(reqDTO.getExpectedPosition());
            newExperience.setAdvantages(reqDTO.getAdvantages());
            newExperience.setBirthDate(reqDTO.getBirthDate());
            newExperience.setName(reqDTO.getName());
            newExperience.setPhone(reqDTO.getPhone());
            newExperience.setWechat(reqDTO.getWechat());
            newExperience.setGender(reqDTO.getGender());
            boolean saveSuccess = onlineResumeService.save(newExperience);
            if (saveSuccess) {
                return Result.ok();
            }
            return Result.fail("创建记录失败");
        }
    }
}
