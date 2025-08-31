package com.ShiXi.user.IdentityAuthentication.teacherIdentification.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 教师身份认证实体类
 */
@Data
@Accessors(chain = true)
@TableName("identification_teacher")
public class TeacherIdentification implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // 主键ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    // 用户ID
    private Long userId;
    
    // 教师姓名
    private String name;
    

    
    // 性别
    private String gender;

    
    // 学校名称
    private String university;
    
    // 学院名称
    private String college;
    
    // 入职时间
    private LocalDate joinDate;
    
    // 邮箱地址
    private String email;
    
    // 工作证明
    private String pictureMaterialUrl;
    private String workCertificateNumber;
    private String feedback;
    // 逻辑删除标志，0-未删除，1-已删除
    @TableLogic
    private Integer isDeleted;
}
