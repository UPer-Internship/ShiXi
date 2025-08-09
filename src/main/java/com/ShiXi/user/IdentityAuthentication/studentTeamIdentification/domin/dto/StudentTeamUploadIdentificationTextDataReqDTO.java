package com.ShiXi.user.IdentityAuthentication.studentTeamIdentification.domin.dto;

import lombok.Data;

@Data
public class StudentTeamUploadIdentificationTextDataReqDTO {
    private String teamName;
    private String universityName;
    private String schoolName;
    private String teamLeaderName;
    private Integer teamLeaderGender;
    private String address;
}