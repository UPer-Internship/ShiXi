package com.ShiXi.user.废弃info.studentInfo.domin.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class StudentChangeInfoDTO {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;// 用户id
    private String phone;// 手机号
    private String openid;// 微信openId
    private String password;// 密码
    private String nickName;// 昵称
    private String icon = "";// 头像
    private String gender;
    private String birthDate;
    private String name;
    private String wechat;
    private String schoolName;
    private String major;
    private String graduationDate;
    private String educationLevel;
    private String advantages;
    private String expectedPosition;
}
