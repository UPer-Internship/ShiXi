package com.ShiXi.position.jobPartTime.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("job_part_time")
public class JobPartTime {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Long publisherId;
    
    private Long companyId;
    
    private String title;
    
    private BigDecimal salaryMin;
    
    private BigDecimal salaryMax;
    
    private String mainText;
    
    private String province;
    
    private String city;
    
    private String district;
    
    private String type;
    
    private String tag;
    
    private Integer status;
    
    @TableLogic
    private Integer isDeleted;
    
    private String category;
    
    private String label;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}