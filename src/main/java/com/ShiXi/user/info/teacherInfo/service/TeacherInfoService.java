package com.ShiXi.user.info.teacherInfo.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.info.teacherInfo.domin.dto.TeacherChangeInfoDTO;
import com.ShiXi.user.info.teacherInfo.entity.TeacherInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TeacherInfoService extends IService<TeacherInfo> {
    Result getTeacherInfo();

    Result setMyTeacherInfo(TeacherChangeInfoDTO teacherChangeInfoDTO);
}
