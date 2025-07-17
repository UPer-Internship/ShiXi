package com.ShiXi.dto;
import lombok.Data;
@Data
public class StudentDTO {
    private Long id;
    private String nickName;
    private String icon;
    private enum sex{ MALE, FEMALE;};
    private String phone;
    private String schoolName;
}
