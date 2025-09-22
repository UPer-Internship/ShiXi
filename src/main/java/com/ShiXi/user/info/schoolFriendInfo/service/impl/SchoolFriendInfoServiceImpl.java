package com.ShiXi.user.info.schoolFriendInfo.service.impl;

import cn.hutool.json.JSONUtil;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.SchoolFriendInfoMapper;
import com.ShiXi.common.mapper.StudentInfoMapper;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.info.schoolFriendInfo.domin.dto.SchoolFriendChangeInfoDTO;
import com.ShiXi.user.info.schoolFriendInfo.entity.SchoolFriendInfo;
import com.ShiXi.user.info.schoolFriendInfo.service.SchoolFriendInfoService;
import com.ShiXi.user.info.studentInfo.domin.dto.StudentChangeInfoDTO;
import com.ShiXi.user.info.studentInfo.domin.vo.StudentInfoVO;
import com.ShiXi.user.info.studentInfo.entity.StudentInfo;

import com.ShiXi.user.info.studentInfo.service.StudentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class SchoolFriendInfoServiceImpl extends ServiceImpl<SchoolFriendInfoMapper, SchoolFriendInfo> implements SchoolFriendInfoService {
    @Override
    public Result getSchoolFriendInfo() {
        //获取当前用户id
        Long id = UserHolder.getUser().getId();
        //获取用户的student信息
        SchoolFriendInfo schoolFriendInfo = lambdaQuery().eq(SchoolFriendInfo::getUserId, id).one();
        //构造返回对象
        StudentInfoVO studentInfoVO= new StudentInfoVO();

        studentInfoVO.setSchoolName(schoolFriendInfo.getSchoolName());
        studentInfoVO.setMajor(schoolFriendInfo.getMajor());
        studentInfoVO.setGraduationDate(schoolFriendInfo.getGraduationDate());
        studentInfoVO.setEducationLevel(schoolFriendInfo.getEducationLevel());
        studentInfoVO.setAdvantages(JSONUtil.toList(schoolFriendInfo.getAdvantages(),String.class));
        studentInfoVO.setExpectedPosition(JSONUtil.toList(schoolFriendInfo.getExpectedPosition(),String.class));
        studentInfoVO.setTags(JSONUtil.toList(schoolFriendInfo.getTags(), String.class));

        return  Result.ok(studentInfoVO);
    }

    @Override
    public Result setMySchoolFriendInfo(SchoolFriendChangeInfoDTO schoolFriendChangeInfoDTO) {
        Long userId = UserHolder.getUser().getId();
        lambdaUpdate().eq(SchoolFriendInfo::getUserId, userId)
                .set(SchoolFriendInfo::getAdvantages, JSONUtil.toJsonStr(schoolFriendChangeInfoDTO.getAdvantages()))
                .set(SchoolFriendInfo::getExpectedPosition, JSONUtil.toJsonStr(schoolFriendChangeInfoDTO.getExpectedPosition()))
                .update();
        return Result.ok();
    }
}
