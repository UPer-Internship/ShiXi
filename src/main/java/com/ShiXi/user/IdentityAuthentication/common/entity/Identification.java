package com.ShiXi.user.IdentityAuthentication.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("identification")
public class Identification implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;// 自增id
    private Long userId;
    private Integer isStudent;
    private Integer isSchoolFriend;
    private Integer isTeacher;
    private Integer isEnterprise;
    private Integer isAdmin;
    private Integer isStudentTeam;

}
