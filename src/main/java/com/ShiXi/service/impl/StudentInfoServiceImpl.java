package com.ShiXi.service.impl;

import com.ShiXi.dto.Result;
import com.ShiXi.dto.UserDTO;
import com.ShiXi.entity.ResumeExperience;
import com.ShiXi.entity.StudentInfo;
import com.ShiXi.mapper.ResumeExperienceMapper;
import com.ShiXi.mapper.StudentInfoMapper;
import com.ShiXi.service.StudentInfoService;
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
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements StudentInfoService {
    @Resource
    private StudentInfoMapper studentInfoMapper;
    @Resource
    private ResumeExperienceMapper resumeExperienceMapper;

    // 保存学生基本信息
    // 基本信息包括姓名、性别、出生日期、电话, 学校名称、专业
    @Override
    public Result saveStudentBasicInfo(StudentInfo studentInfo){
        UserDTO user = UserHolder.getUser();
        Long userId = user.getId();

        //检查是否有信息为空
        if(studentInfo.getName() == null
        || studentInfo.getGender() == null
        || studentInfo.getBirthDate() == null
        ||  studentInfo.getPhone() == null
        || studentInfo.getSchoolName() == null
        || studentInfo.getMajor() == null){
            return Result.fail("请填写完整信息");
        }
        //studentInfo.setId(userId);
        studentInfo.setUserId(userId);
        save(studentInfo);
        return Result.ok();
    }

    @Override
    public Result getStudentBasicInfo(){
        UserDTO user = UserHolder.getUser();
        System.out.println(user);
        return Result.ok(user);
    }

    @Override
    public Result changeStudentBasicInfo(StudentInfo studentInfo){
         //Long userId = 1L;
         Long userId = UserHolder.getUser().getId();
         studentInfo.setUserId(userId);
         updateById(studentInfo);
         return Result.ok();
    }

}
