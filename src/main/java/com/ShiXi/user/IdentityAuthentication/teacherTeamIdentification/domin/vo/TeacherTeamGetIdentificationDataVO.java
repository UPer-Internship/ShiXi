package com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.domin.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TeacherTeamGetIdentificationDataVO {
    private String Name;
    private String SchoolName;
    private String major;
    private String teacherIdCardUrl;
}
