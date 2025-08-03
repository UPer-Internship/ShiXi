package com.ShiXi.user.info.common.domin.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class changeInfoDTO {
    //公用属性
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;// 用户id
    private String identification;
    private String nickName;// 昵称
    private String icon = "";// 头像
    private String gender;
    private String birthDate;
    private String name;
    private String wechat;
    //学生属性
    private String schoolName;
    private String major;
    private String graduationDate;
    private String educationLevel;
    private String advantages;
    private String expectedPosition;

}
