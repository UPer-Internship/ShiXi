package com.ShiXi.user.info.teacherInfo.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.info.studentInfo.domin.dto.StudentChangeInfoDTO;
import com.ShiXi.user.info.teacherInfo.domin.dto.TeacherChangeInfoDTO;

public interface TeacherInfoService {
    Result getTeacherInfo();

    Result setMyTeacherInfo(TeacherChangeInfoDTO teacherChangeInfoDTO);
}
