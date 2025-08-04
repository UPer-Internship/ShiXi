package com.ShiXi.user.IdentityAuthentication.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.IdentificationMapper;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.IdentityAuthentication.common.domin.dto.InitiateIdentificationDTO;
import com.ShiXi.user.IdentityAuthentication.common.domin.vo.IdentificationVO;
import com.ShiXi.user.IdentityAuthentication.common.entity.Identification;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.service.StudentIdentificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@Service
public class IdentificationServiceImpl extends ServiceImpl<IdentificationMapper, Identification> implements IdentificationService {
   @Resource
   StudentIdentificationService studentIdentificationService;

    @Override
    public Result getIdentification() {
        //获取用户id
        Long id = UserHolder.getUser().getId();
        Identification identification = lambdaQuery()
                .eq(Identification::getUserId, id)  // 直接引用实体类的userId字段
                .one();
        IdentificationVO identificationVO=new IdentificationVO();
        BeanUtil.copyProperties(identification, identificationVO);
        //构造返回对象
        return Result.ok(identificationVO);
    }

    @Override
    public Result toIdentification(String  identification, String type, MultipartFile file) {
        if(identification.equals("student")){
           return studentIdentificationService.toIdentification(type,file);

        }
        else if(identification.equals("teacher")){

        }
        else if(identification.equals("schoolFriend")){

        }
        else if(identification.equals("enterprise")){

        }
        return Result.fail("发生错误");
    }

    @Override
    public Result getMyIdentification(String identification, String type) {
        if(identification.equals("student")){
            return studentIdentificationService.getMyIdentification(identification,type);
        }
        else if(identification.equals("teacher")){

        }
        else if(identification.equals("schoolFriend")){

        }
        else if(identification.equals("enterprise")){

        }
        return Result.fail("发生错误");
    }
}
