package com.ShiXi.user.IdentityAuthentication.studentTeamIdentification.domin.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StudentTeamGetIdentificationDataVO {
    private Long userId;
    private String identification;
    private String teamName;
    private String universityName;
    private String schoolName;
    private String teamLeaderName;
    private Integer teamLeaderGender;
    private String address;
    private String identificationImagesUrl;
}