package com.ShiXi.user.IdentityAuthentication.studentIdentification.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("student_identification")
public class StudentIdentification implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String pictureMaterialUrl;
    private String name;
    private String gender;
    private String birthDate;
    private String educationLevel;
    private String university;
    private String major;
    private String enrollmentDate;
    private String graduationDate;
    private String studentCardNumber;



}
