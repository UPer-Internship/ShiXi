package com.ShiXi.user.info.teacherInfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_teacher_info")
public class TeacherInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    // 学校名称
    private String university;

    // 学院名称
    private String college;

    // 入职时间
    private LocalDate joinDate;

    // 邮箱地址
    private String email;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
    @TableLogic
    private Integer isDeleted;// 逻辑删除标志，0-未删除，1-已删除
}
