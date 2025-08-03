package com.ShiXi.user.info.studentInfo.domin.dto;
import lombok.Data;
@Data
@Deprecated
public class StudentDTO {
    private Long id;
    private String nickName;
    private String icon;
    private enum sex{ MALE, FEMALE;};
    private String phone;
    private String schoolName;
}
