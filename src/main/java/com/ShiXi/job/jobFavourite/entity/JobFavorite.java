package com.ShiXi.job.jobFavourite.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("job_favorite")
public class JobFavorite implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long jobId;
    private LocalDateTime createTime;
    @TableLogic
    private Integer isDeleted; // 逻辑删除标志，0-未删除，1-已删除
} 