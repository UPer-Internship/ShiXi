package com.ShiXi.user.common.domin.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChangeInfoDTO {
    //公用属性
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;// 用户id
    private String nickName;
//    private String icon = "";
    private String gender;
    private String birthDate;
    private String name;
    private String wechat;


}
