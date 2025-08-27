package com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
@TableName("school_friend_identification")
public class SchoolFriendIdentification implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    private Long userId;


    private String name;


    private String gender;

    private LocalDate birthDate;


    private String educationLevel;


    private String university;


    private String major;


    private LocalDate enrollmentDate;


    private LocalDate graduationDate;

    // 毕业证书
    private String pictureMaterialUrl;
    // 毕业证书
    private String  graduationCertificateNumber;

    // 逻辑删除标志，0-未删除，1-已删除
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;
}