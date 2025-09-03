package com.ShiXi.user.废弃info.studentInfo.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.废弃info.studentInfo.domin.dto.StudentChangeInfoDTO;
import com.ShiXi.user.废弃info.studentInfo.entity.StudentInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface StudentInfoService extends IService<StudentInfo> {
    /**
     * 保存学生基本信息
     */
//    Result saveStudentBasicInfo(studentBasicInfoReqDTO reqDTO);

    /**
     * 获取我的基本信息
     */
//    Result getStudentBasicInfo();

    /**
     * 修改学生基本信息
     */
//    Result changeStudentBasicInfo(StudentInfo studentInfo);
    void changeStudentInfo(StudentChangeInfoDTO reqDTO);
    Result getStudentInfo();
}
