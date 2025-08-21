package com.ShiXi.user.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;// 用户id

    private String uuid;// 用户唯一标识符

    private String phone;// 手机号

    private String openid;// 微信openId

    private String password;// 密码

    private String nickName;// 昵称

    private String icon = "";// 头像

    private LocalDateTime createTime;// 注册时间

    private LocalDateTime updateTime;// 更新时间

    @TableLogic
    private Integer isDeleted;// 逻辑删除标志，0-未删除，1-已删除

    private String gender;

    private String birthDate;

    private String name;

    private String wechat;

}
