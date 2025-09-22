package com.ShiXi.user.info.teacherInfo.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.TeacherInfoMapper;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.info.teacherInfo.domin.dto.TeacherChangeInfoDTO;
import com.ShiXi.user.info.teacherInfo.domin.vo.TeacherInfoVO;
import com.ShiXi.user.info.teacherInfo.entity.TeacherInfo;
import com.ShiXi.user.info.teacherInfo.service.TeacherInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TeacherInfoImpl extends ServiceImpl<TeacherInfoMapper, TeacherInfo> implements TeacherInfoService {
    @Override
    public Result getTeacherInfo() {
        // 获取当前用户ID
        Long userId = UserHolder.getUser().getId();

        // 查询教师信息
        TeacherInfo teacherInfo = lambdaQuery()
                .eq(TeacherInfo::getUserId, userId)
                .one();

        // 如果没有教师信息，返回空对象
        if (teacherInfo == null) {
            return Result.ok(new TeacherInfoVO());
        }

        // 构造返回对象
        TeacherInfoVO teacherInfoVO = new TeacherInfoVO();
        teacherInfoVO.setUserId(teacherInfo.getUserId());
        teacherInfoVO.setUniversity(teacherInfo.getUniversity());
        teacherInfoVO.setCollege(teacherInfo.getCollege());
        teacherInfoVO.setJoinDate(teacherInfo.getJoinDate());
        teacherInfoVO.setEmail(teacherInfo.getEmail());

        return Result.ok(teacherInfoVO);
    }

    @Override
    public Result setMyTeacherInfo(TeacherChangeInfoDTO teacherChangeInfoDTO) {
        // 获取当前用户ID
        Long userId = UserHolder.getUser().getId();

        // 查询是否已存在教师信息
        TeacherInfo existingTeacherInfo = lambdaQuery()
                .eq(TeacherInfo::getUserId, userId)
                .one();

        // 创建或更新教师信息实体
        TeacherInfo teacherInfo = new TeacherInfo();
        teacherInfo.setUniversity(teacherChangeInfoDTO.getUniversity());
        teacherInfo.setCollege(teacherChangeInfoDTO.getCollege());
        teacherInfo.setJoinDate(teacherChangeInfoDTO.getJoinDate());
        teacherInfo.setEmail(teacherChangeInfoDTO.getEmail());

        if (existingTeacherInfo != null) {
            // 更新已有记录
            teacherInfo.setId(existingTeacherInfo.getId());
            teacherInfo.setUserId(userId);
            updateById(teacherInfo);
        } else {
            // 新增记录
            teacherInfo.setUserId(userId);
            save(teacherInfo);
        }

        return Result.ok();
    }
}
