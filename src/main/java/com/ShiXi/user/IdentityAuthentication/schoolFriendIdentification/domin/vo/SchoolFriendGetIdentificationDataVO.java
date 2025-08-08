package com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.domin.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SchoolFriendGetIdentificationDataVO {
    private String graduationCertificateUrl;
    private String identityCardUrl;
}
