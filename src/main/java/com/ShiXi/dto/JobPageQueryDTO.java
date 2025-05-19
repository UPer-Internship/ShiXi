package com.ShiXi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询岗位的查询类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPageQueryDTO {
    private Integer page;//页码
    private Integer pageSize;//每页记录数
    private String type;//岗位类型(实现、兼职、科研课题)
    private String category;//岗位类别(UI设计等tag)
}
