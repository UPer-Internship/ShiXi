package com.ShiXi.user.IdentityAuthentication.studentIdentification.domin.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StudentGetIdentificationDataVO {
    private Long userId;

    private String identification;

    private String identityCardUrl;

    private String graduationCertificateUrl;

    private String studentIdCardUrl;
}
