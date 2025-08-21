package com.ShiXi.user.common.domin.dto;


import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String uuid;
    private String nickName;
    private String icon;
    private String phone;
    //当前身份
    private Integer identification;
    private String major;
    private String token;
}
