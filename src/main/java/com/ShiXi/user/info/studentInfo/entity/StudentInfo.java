package com.ShiXi.user.info.studentInfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_student_info")
public class StudentInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String schoolName;

    private String major;

    private String graduationDate;

    private String educationLevel;

    private String advantages;

    private String expectedPosition;

    private String enrollmentDate;

    private String tags;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
    @TableLogic
    private Integer isDeleted;// 逻辑删除标志，0-未删除，1-已删除
}
