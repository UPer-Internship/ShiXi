package com.ShiXi.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("region")
public class Region {

        @TableId(value = "id", type = IdType.AUTO)
        private Long id;
        
        private String province;
        private String city;
        private String district;

}
