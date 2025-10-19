package com.ShiXi.blog.domin.vo;

import lombok.Data;

import java.util.List;
@Data
public class MyBlogListVO {
    private Long id;
    private String title;
    private String content;
    private List<String> images;
}
