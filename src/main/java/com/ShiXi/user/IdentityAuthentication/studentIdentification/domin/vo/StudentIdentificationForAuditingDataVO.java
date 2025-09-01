package com.ShiXi.user.IdentityAuthentication.studentIdentification.domin.vo;

import lombok.Data;

import java.util.List;

@Data
public class StudentIdentificationForAuditingDataVO extends StudentGetIdentificationDataVO{
    //是否被怀疑
    boolean isSuspected=false;
    //被怀疑的关联对象
    private List<Long> suspectedUserIds;
//    private Integer suspectedIdentification;
//    private String suspectedPictureMaterialUrl;
//    private String suspectedName;
//    private String suspectedGender;
//    private String suspectedBirthDate;
//    private String suspectedEducationLevel;
//    private String suspectedUniversity;
//    private String suspectedMajor;
//    private String suspectedEnrollmentDate;
//    private String suspectedGraduationDate;
//    private String suspectedStudentCardNumber;
}
