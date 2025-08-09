package com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.domin.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TeacherTeamGetIdentificationDataVO {
    private Long userId;
    private String identification;
    private String Name;
    private String SchoolName;
    private String major;
    private String teacherIdCardUrl;
}
