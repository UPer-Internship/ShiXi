package com.ShiXi.user.IdentityAuthentication.studentIdentification.domin.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StudentGetIdentificationDataVO {
    private String identityCardUrl;

    private String graduationCertificateUrl;

    private String studentIdCardUrl;
}
