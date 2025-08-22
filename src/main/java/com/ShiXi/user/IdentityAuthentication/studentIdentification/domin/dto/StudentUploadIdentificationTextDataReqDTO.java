package com.ShiXi.user.IdentityAuthentication.studentIdentification.domin.dto;

import lombok.Data;

@Data
public class StudentUploadIdentificationTextDataReqDTO {
    private String name;
    private String gender;
    private String birthDate;
    private String educationLevel;
    private String university;
    private String major;
    private String enrollmentDate;
    private String graduationDate;

}
