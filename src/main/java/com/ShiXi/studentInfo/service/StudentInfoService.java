package com.ShiXi.studentInfo.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.studentInfo.domin.dto.studentBasicInfoReqDTO;
import com.ShiXi.studentInfo.entity.StudentInfo;

public interface StudentInfoService {
    /**
     * 保存学生基本信息
     */
    Result saveStudentBasicInfo(studentBasicInfoReqDTO reqDTO);

    /**
     * 获取我的基本信息
     */
    Result getStudentBasicInfo();

    /**
     * 修改学生基本信息
     */
    Result changeStudentBasicInfo(StudentInfo studentInfo);

}
