package com.ShiXi.user.IdentityAuthentication.teacherIdentification.domin.vo;

import lombok.Data;

import java.util.List;

@Data
public class TeacherIdentificationForAuditingDataVO extends TeacherGetIdentificationDataVO {
    //是否被怀疑
    boolean isSuspected = false;
    //被怀疑的关联对象
    private List<Long> suspectedUserIds;
}
