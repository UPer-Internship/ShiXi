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

    // 用户ID
    @TableField("user_id")
    private Long userId;

    // 姓名
    @TableField("name")
    private String name;

    // 性别
    @TableField("gender")
    private String gender;

    // 出生日期
    @TableField("birth_date")
    private LocalDate birthDate;

    // 学历
    @TableField("education")
    private String education;

    // 学校名称
    @TableField("school")
    private String school;

    // 专业名称
    @TableField("major")
    private String major;

    // 入学时间
    @TableField("begin_time")
    private LocalDate beginTime;

    // 毕业时间
    @TableField("graduation_time")
    private LocalDate graduationTime;

    // 毕业证书
    @TableField("graduation_certificate")
    private String graduationCertificate;

    // 逻辑删除标志，0-未删除，1-已删除
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;
}