package com.ShiXi.user.IdentityAuthentication.common.domin.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Accessors(chain = true)
public class InitiateIdentificationDTO {
    private String identification;
    private List<identificationMaterial> identificationMaterials;
    @Data
    public static class identificationMaterial {
        private String type;
        private MultipartFile file;
    }

}
