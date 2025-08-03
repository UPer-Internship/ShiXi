package com.ShiXi.user.info.studentInfo.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.UserMapper;
import com.ShiXi.user.common.domin.dto.UserDTO;
import com.ShiXi.user.common.service.UserService;
import com.ShiXi.user.info.studentInfo.domin.dto.StudentChangeInfoDTO;
import com.ShiXi.user.info.studentInfo.domin.vo.StudentInfoVO;
import com.ShiXi.user.info.studentInfo.entity.StudentInfo;
import com.ShiXi.common.mapper.ResumeExperienceMapper;
import com.ShiXi.common.mapper.StudentInfoMapper;
import com.ShiXi.user.info.studentInfo.service.StudentInfoService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.common.entity.User;
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
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserService userService;
    // 保存学生基本信息
    // 基本信息包括姓名、性别、出生日期、电话, 学校名称、专业
//    @Override
//    public Result saveStudentBasicInfo(studentBasicInfoReqDTO reqDTO){
//        //获取当前用户id
//        UserDTO user = UserHolder.getUser();
//        Long userId = user.getId();
//        //创建两个实体类
//        StudentInfo studentInfo = new StudentInfo();
//        User userInfo = new User();
//        //填充studentInfo
//        {
//            studentInfo.setUserId(userId);
//            studentInfo.setSchoolName(reqDTO.getSchoolName());
//            studentInfo.setMajor(reqDTO.getMajor());
//        }
//        //填充userInfo
//        {
//            userInfo.setId(userId);
//            userInfo.setGender(reqDTO.getGender());
//            userInfo.setName(reqDTO.getName());
//            userInfo.setNickName(reqDTO.getNickName());
//            userInfo.setBirthDate(reqDTO.getBirthDate());
//        }
//        //保存user信息
//        userMapper.updateById(userInfo);
//        //保存student信息
//        StudentInfo existingStudent = query().eq("user_id", userId).one();
//        if (existingStudent != null) {
//            // 存在则更新，设置主键ID
//            studentInfo.setId(existingStudent.getId());
//            // 如果需要保留原有未修改的字段，可以从existingStudent中获取
//            updateById(studentInfo);
//        } else {
//            // 不存在则新增
//            save(studentInfo);
//        }
//        //返回
//        return Result.ok();
//    }

//    @Override
//    public Result getStudentBasicInfo(){
//        UserDTO user = UserHolder.getUser();
//        Long userId=user.getId();
//
//        return Result.ok(user);
//    }

//    @Override
//    public Result changeStudentBasicInfo(StudentInfo studentInfo){
//         //Long userId = 1L;
//         Long userId = UserHolder.getUser().getId();
//         studentInfo.setId(userId);
//         updateById(studentInfo);
//         return Result.ok();
//    }


    @Override
    public void changeStudentInfo(StudentChangeInfoDTO reqDTO) {
        //获取当前用户id
        UserDTO user = UserHolder.getUser();
        Long userId = user.getId();
        //创建两个实体类
        StudentInfo studentInfo = new StudentInfo();
        User userInfo = new User();
        //填充studentInfo
        {
            studentInfo.setUserId(userId);
            studentInfo.setSchoolName(reqDTO.getSchoolName());
            studentInfo.setMajor(reqDTO.getMajor());
            studentInfo.setEducationLevel(reqDTO.getEducationLevel());
            studentInfo.setGraduationDate(reqDTO.getGraduationDate());
            studentInfo.setAdvantages(reqDTO.getAdvantages());
            studentInfo.setExpectedPosition(reqDTO.getExpectedPosition());
        }
        //填充userInfo
        {
            userInfo.setId(userId);
            userInfo.setGender(reqDTO.getGender());
            userInfo.setName(reqDTO.getName());
            userInfo.setNickName(reqDTO.getNickName());
            userInfo.setBirthDate(reqDTO.getBirthDate());
            userInfo.setIcon(reqDTO.getIcon());
            userInfo.setName(reqDTO.getName());
            userInfo.setWechat(reqDTO.getWechat());
        }
        //保存user信息
        userMapper.updateById(userInfo);
        //保存student信息
        StudentInfo existingStudent = query().eq("user_id", userId).one();
        if (existingStudent != null) {
            // 存在则更新，设置主键ID
            studentInfo.setId(existingStudent.getId());
            // 如果需要保留原有未修改的字段，可以从existingStudent中获取
            updateById(studentInfo);
        } else {
            // 不存在则新增
            save(studentInfo);
        }
        //返回
    }

    @Override
    public Result getStudentInfo() {
        //获取当前用户id
        Long id = UserHolder.getUser().getId();
        //获取用户的student信息
        StudentInfo studentInfo = query().eq("user_id", id).one();
        //获取用户的user信息
        User userInfo = userService.getById(id);
        //构造返回对象
        StudentInfoVO studentInfoVO=new StudentInfoVO();

        studentInfoVO.setUserId(userInfo.getId());
        studentInfoVO.setPhone(userInfo.getPhone());
        studentInfoVO.setNickName(userInfo.getNickName());
        studentInfoVO.setIcon(userInfo.getIcon());
        studentInfoVO.setGender(userInfo.getGender());
        studentInfoVO.setBirthDate(userInfo.getBirthDate());
        studentInfoVO.setName(userInfo.getName());
        studentInfoVO.setWechat(userInfo.getWechat());

        studentInfoVO.setSchoolName(studentInfo.getSchoolName());
        studentInfoVO.setMajor(studentInfo.getMajor());
        studentInfoVO.setGraduationDate(studentInfo.getGraduationDate());
        studentInfoVO.setEducationLevel(studentInfo.getEducationLevel());
        studentInfoVO.setAdvantages(studentInfo.getAdvantages());
        studentInfoVO.setExpectedPosition(studentInfo.getExpectedPosition());

        return  Result.ok(studentInfoVO);
    }

}
