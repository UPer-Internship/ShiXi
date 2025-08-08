package com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.domin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EnterpriseUploadIdentificationTextDataReqDTO {
    private String enterpriseName;
    private String enterpriseType;
    private String enterpriseScale;
}
