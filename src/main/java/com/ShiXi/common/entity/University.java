package com.ShiXi.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("university")
public class University {
    private Integer id;
    private String universityName;
    private String designation; // 985 211
}