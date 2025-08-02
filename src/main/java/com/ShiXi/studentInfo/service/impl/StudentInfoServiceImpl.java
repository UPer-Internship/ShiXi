package com.ShiXi.studentInfo.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.domin.dto.UserDTO;
import com.ShiXi.studentInfo.domin.dto.studentBasicInfoReqDTO;
import com.ShiXi.studentInfo.entity.StudentInfo;
import com.ShiXi.common.mapper.ResumeExperienceMapper;
import com.ShiXi.common.mapper.StudentInfoMapper;
import com.ShiXi.studentInfo.service.StudentInfoService;
import com.ShiXi.common.utils.UserHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public Result saveStudentBasicInfo(studentBasicInfoReqDTO reqDTO){
        UserDTO user = UserHolder.getUser();
        Long userId = user.getId();
        String phone= user.getPhone();
        //检查是否有信息为空
        if(reqDTO.getName() == null
        ||reqDTO.getGender() == null
        ||reqDTO.getBirthDate() == null
        ||reqDTO.getSchoolName() == null
        ||reqDTO.getMajor() == null){
            return Result.fail("请填写完整信息");
        }
        StudentInfo existingStudent = query().eq("user_id", userId).one();
        StudentInfo studentInfo = new StudentInfo();
        {
            studentInfo.setUserId(userId);
            studentInfo.setName(reqDTO.getName());
            studentInfo.setGender(reqDTO.getGender());
            studentInfo.setBirthDate(reqDTO.getBirthDate());
            studentInfo.setSchoolName(reqDTO.getSchoolName());
            studentInfo.setMajor(reqDTO.getMajor());
            studentInfo.setPhone(phone);
        }
        if (existingStudent != null) {
            // 存在则更新，设置主键ID
            studentInfo.setId(existingStudent.getId());
            // 如果需要保留原有未修改的字段，可以从existingStudent中获取
            updateById(studentInfo);
        } else {
            // 不存在则新增
            save(studentInfo);
        }
        return Result.ok();
    }

    @Override
    public Result getStudentBasicInfo(){
        UserDTO user = UserHolder.getUser();
        return Result.ok(user);
    }

    @Override
    public Result changeStudentBasicInfo(StudentInfo studentInfo){
         //Long userId = 1L;
         Long userId = UserHolder.getUser().getId();
         studentInfo.setId(userId);
         updateById(studentInfo);
         return Result.ok();
    }

}
