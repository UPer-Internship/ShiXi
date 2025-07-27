package com.ShiXi.login.domin.dto;


import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String nickName;
    private String icon;
    private String phone;
    private String major; // 专业
}
