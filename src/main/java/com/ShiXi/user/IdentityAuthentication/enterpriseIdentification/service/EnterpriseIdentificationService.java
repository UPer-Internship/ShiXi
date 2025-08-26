package com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.domin.dto.EnterpriseUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.domin.vo.EnterpriseGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.entity.EnterpriseIdentification;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 企业身份认证服务接口
 */
public interface EnterpriseIdentificationService extends IService<EnterpriseIdentification> {
    
    /**
     * 上传企业认证图片资料（营业执照等证件）
     * @param file 图片文件
     * @return 操作结果
     */
    Result uploadIdentificationPictureData(MultipartFile file);
    
    /**
     * 查看当前用户的企业认证资料
     * @return 企业认证数据
     */
    Result getMyIdentificationData();
    
    /**
     * 根据用户ID获取企业认证数据
     * @param userId 用户ID
     * @return 企业认证数据VO
     */
    EnterpriseGetIdentificationDataVO getIdentificationDataByUserId(Long userId);
    
    /**
     * 上传企业认证文本数据
     * @param reqDTO 企业认证文本数据请求DTO
     * @return 操作结果
     */
    Result uploadIdentificationTextData(EnterpriseUploadIdentificationTextDataReqDTO reqDTO);
}