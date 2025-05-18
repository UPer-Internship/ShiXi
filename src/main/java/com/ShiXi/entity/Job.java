package com.ShiXi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("job")
public class Job implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
  //  @TableField("publisher_id")
    private Long publisherId;
    private String title;
    private String salary;//薪水
    private String Frequency;//4天/周
   // @TableField("total_time")
    private String total_time;//4个月
   // @TableField("enterprise_type")
    private String enterprise_type;//外企 校友企业
    private String Publisher;//hr名字
  //  @TableField("enterprise_name")
    private String enterprise_name;//腾讯 欧莱雅 阿里巴巴
  //  @TableField("financing_progress")
    private String financing_progress;//A轮融资 D轮 不需要
   // @TableField("enterprise_scale")
    private String enterprise_scale;//10000人 100人
   // @TableField("work_location")
    private String work_location;//珠海 横琴
    //@TableField("detailed_information")
    private String detailed_information;//职位的详情信息
    private String category;//UI设计 游戏策划 ...
    private String type;//实习 兼职 课题
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
