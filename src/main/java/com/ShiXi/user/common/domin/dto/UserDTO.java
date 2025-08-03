package com.ShiXi.user.common.domin.dto;


import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String nickName;
    private String icon;
    private String phone;
    private String major; // 专业
    //当前身份
    private String identification;
}
