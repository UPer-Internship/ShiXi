package com.ShiXi.user.IdentityAuthentication.studentIdentification.domin.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StudentGetIdentificationDataVO {
    private Long userId;
    private Integer identification;
    private String pictureMaterialUrl;
    private String name;
    private String gender;
    private String birthDate;
    private String educationLevel;
    private String university;
    private String major;
    private String enrollmentDate;
    private String graduationDate;
}
