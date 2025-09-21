package com.ShiXi.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("industry")
public class Industry {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String industryName;
    private String firstLevelCategoryLabel;
}
