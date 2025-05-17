package com.ShiXi.service.impl;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.StudentBasicInfo;
import com.ShiXi.mapper.StudentBasicInfoMapper;
import com.ShiXi.service.StudentBasicInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class StudentBasicInfoImpl extends ServiceImpl<StudentBasicInfoMapper, StudentBasicInfo> implements StudentBasicInfoService {
    @Resource
    private StudentBasicInfoMapper studentBasicInfoMapper;

    @Override
    public Result saveStudentBasicInfo(StudentBasicInfo studentBasicInfo) {
        //检查是否有信息为空
        if (studentBasicInfo.getName() == null
                || studentBasicInfo.getGender() == null
                || studentBasicInfo.getBirthDate() == null
                || studentBasicInfo.getHighestEducation() == null
                || studentBasicInfo.getSchoolName() == null
                || studentBasicInfo.getMajor() == null
                || studentBasicInfo.getStudyPeriod() == null
                || studentBasicInfo.getExpectedPosition() == null) {
            return Result.fail("请填写完整信息");
        }
        save(studentBasicInfo);
        return Result.ok();
    }
}
