package com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.domin.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EnterpriseGetIdentificationDataVO {
    private String enterpriseName;
    private String enterpriseIdCard;
    private String enterpriseType;
    private String enterpriseScale;
}
