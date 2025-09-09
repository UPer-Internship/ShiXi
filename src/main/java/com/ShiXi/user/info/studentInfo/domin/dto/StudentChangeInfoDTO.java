package com.ShiXi.user.info.studentInfo.domin.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class StudentChangeInfoDTO {
    private List<String> advantages;
    private List<String> expectedPosition;
}
