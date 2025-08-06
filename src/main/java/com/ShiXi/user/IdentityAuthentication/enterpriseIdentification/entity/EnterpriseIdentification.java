package com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("enterprise_identification")
public class EnterpriseIdentification implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private String enterpriseName;
    
    private String enterpriseType;
    
    private String enterpriseScale;
    
    private String enterpriseIdCard;
    @TableLogic
    private Integer isDeleted;
}
