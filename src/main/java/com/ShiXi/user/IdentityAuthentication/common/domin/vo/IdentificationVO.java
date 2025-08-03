package com.ShiXi.user.IdentityAuthentication.common.domin.vo;

import lombok.Data;

@Data
public class IdentificationVO {
    private Integer isStudent;
    private Integer isSchoolFriend;
    private Integer isTeacher;
    private Integer isEnterprise;
}
