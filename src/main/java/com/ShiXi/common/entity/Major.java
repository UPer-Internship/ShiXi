package com.ShiXi.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("major")
public class Major {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String major;
    private String secondLevelCategoryLabel;
    private String firstLevelCategoryLabel;
}
