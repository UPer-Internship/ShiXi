package com.ShiXi.user.IdentityAuthentication.teacherIdentification.domin.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TeacherGetIdentificationDataVO {
    private Long userId;
    private String identification;
    private String Name;
    private String SchoolName;
    private String major;
    private String teacherIdCardUrl;
}
