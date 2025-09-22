package com.ShiXi.user.info.schoolFriendInfo.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.info.schoolFriendInfo.domin.dto.SchoolFriendChangeInfoDTO;
import com.ShiXi.user.info.schoolFriendInfo.entity.SchoolFriendInfo;
import com.ShiXi.user.info.studentInfo.domin.dto.StudentChangeInfoDTO;
import com.ShiXi.user.info.studentInfo.entity.StudentInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SchoolFriendInfoService extends IService<SchoolFriendInfo> {
    Result getSchoolFriendInfo();

    Result setMySchoolFriendInfo(SchoolFriendChangeInfoDTO schoolFriendChangeInfoDTO);
}
