package com.ShiXi.blog.domin.dto;

import lombok.Data;

@Data
public class BasicPageQueryReqDTO {
    private Integer pageNum =1;
    private Integer pageSize =10;
}
