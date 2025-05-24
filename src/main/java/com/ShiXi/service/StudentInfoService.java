package com.ShiXi.service;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.StudentInfo;

public interface StudentInfoService {
    /**
     * 保存学生基本信息
     */
    Result saveStudentBasicInfo(StudentInfo studentInfo);

    /**
     * 获取我的基本信息
     */
    Result getStudentBasicInfo();

    /**
     * 修改学生基本信息
     */
    Result changeStudentBasicInfo(StudentInfo studentInfo);

}
