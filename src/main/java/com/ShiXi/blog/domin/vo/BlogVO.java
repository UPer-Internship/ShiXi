package com.ShiXi.blog.domin.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BlogVO {

    private Long id;

    private Long userId;

    private String title;

    private List<String> images;
    private String cover;
    /**
     * 探店的文字描述
     */
    private String content;

    /**
     * 点赞数量
     */
    private Integer likes = 0;

    /**
     * 评论数量
     */
    private Integer comments = 0;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
