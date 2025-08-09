package com.ShiXi.user.IdentityAuthentication.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.IdentificationMapper;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.IdentityAuthentication.common.domin.dto.DeserializeUserIdAndIdentificationInRedisListDTO;
import com.ShiXi.user.IdentityAuthentication.common.domin.vo.IdentificationVO;
import com.ShiXi.user.IdentityAuthentication.common.entity.CurrentIdentification;
import com.ShiXi.user.IdentityAuthentication.common.entity.Identification;
import com.ShiXi.user.IdentityAuthentication.common.service.CurrentIdentificationService;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.domin.vo.EnterpriseGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.domin.vo.SchoolFriendGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.domin.vo.StudentGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.service.StudentIdentificationService;
import com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.domin.vo.TeacherTeamGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.entity.TeacherTeamIdentification;
import com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.service.TeacherTeamIdentificationService;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.service.EnterpriseIdentificationService;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.service.SchoolFriendIdentificationService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class IdentificationServiceImpl extends ServiceImpl<IdentificationMapper, Identification> implements IdentificationService {
    @Resource
    StudentIdentificationService studentIdentificationService;
   
    @Resource
    TeacherTeamIdentificationService teacherTeamIdentificationService;
   
    @Resource
    EnterpriseIdentificationService enterpriseIdentificationService;
   
    @Resource
    SchoolFriendIdentificationService schoolFriendIdentificationService;

    @Resource
    CurrentIdentificationService currentIdentificationService;;
    @Resource
    StringRedisTemplate stringRedisTemplate;


    @Override
    public Result getIdentificationStatus() {
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


//    @Override
//    public Result getIdentificationDataByUserId(Integer userId, String identification) {
//        if(identification.equals("student")){
//            return studentIdentificationService.getIdentificationDataByUserId(userId);
//        }
//        else if(identification.equals("teacher")){
//            return teacherTeamIdentificationService.getIdentificationDataByUserId(userId);
//        }
//        else if(identification.equals("schoolFriend")){
//            return schoolFriendIdentificationService.getIdentificationDataByUserId(userId);
//        }
//        else if(identification.equals("enterprise")){
//            return enterpriseIdentificationService.getIdentificationDataByUserId(userId);
//        }
//        return Result.fail("发生错误");
//    }

    @Override
    public Result changeIdentification(String identification) {
        Long userId = UserHolder.getUser().getId();
        CurrentIdentification currentIdentification = currentIdentificationService.lambdaQuery().eq(CurrentIdentification::getUserId, userId).one();
        Identification identificationStatus = lambdaQuery().eq(Identification::getUserId, userId).one();
        if(identification==null){
            Result.fail("请选择身份");
        }
        else if (identification.equals("student")) {
            if(identificationStatus.getIsStudent()==2){
                boolean success =currentIdentificationService.lambdaUpdate()
                        .eq(CurrentIdentification::getUserId, userId)
                        .set(CurrentIdentification::getCurrentIdentification, 1)
                        .update();
                if( success){
                    return Result.ok();
                }

            }
            else return Result.fail("此身份未验证");
        }
        else if (identification.equals("schoolFriend")) {
            if(identificationStatus.getIsSchoolFriend()==2){
                boolean success =currentIdentificationService.lambdaUpdate()
                        .eq(CurrentIdentification::getUserId, userId)
                        .set(CurrentIdentification::getCurrentIdentification, 2)
                        .update();
                if( success){
                    return Result.ok();
                }
            }
            else return Result.fail("此身份未验证");
        }
        else if (identification.equals("teacher")) {
            if(identificationStatus.getIsTeacher()==2){
                boolean success =currentIdentificationService.lambdaUpdate()
                        .eq(CurrentIdentification::getUserId, userId)
                        .set(CurrentIdentification::getCurrentIdentification, 3)
                        .update();
                if( success){
                    return Result.ok();
                }
            }
            else return Result.fail("此身份未验证");
        }
        else if (identification.equals("enterprise")) {
            if(identificationStatus.getIsEnterprise()==2){
                boolean success = currentIdentificationService.lambdaUpdate()
                        .eq(CurrentIdentification::getUserId, userId)
                        .set(CurrentIdentification::getCurrentIdentification, 4)
                        .update();
                if( success){
                    return Result.ok();
                }
            }
            else return Result.fail("此身份未验证");
        }
        return  Result.fail("未知错误");
    }

    @Override
    public Result getCurrentIdentification() {
        Long userId = UserHolder.getUser().getId();
        CurrentIdentification currentIdentification = currentIdentificationService.lambdaQuery().eq(CurrentIdentification::getUserId, userId).one();
        if(currentIdentification==null){
            return Result.fail("发生错误");
        }
        Integer identification = currentIdentification.getCurrentIdentification();
        if(identification==null){
            return Result.fail("未使用任何身份登录");
        }
        return Result.ok(identification);
    }

    @Override
    public Result getIdentificationDataRequest() {
        final String key = "waitForAuditingList:";
        String waitForAuditingUserId = stringRedisTemplate.opsForList().leftPop(key);
        if(waitForAuditingUserId==null){
            return Result.ok("暂无需要审核的资料");
        }
        DeserializeUserIdAndIdentificationInRedisListDTO dto = JSONUtil.toBean(waitForAuditingUserId, DeserializeUserIdAndIdentificationInRedisListDTO.class);
        Long userId = dto.getUserId();
        String identification = dto.getIdentification();
        if(identification.equals("student")){
            StudentGetIdentificationDataVO identificationDataByUserId = studentIdentificationService.getIdentificationDataByUserId(userId);
            return Result.ok(identificationDataByUserId);
        }
        else if(identification.equals("teacher")){
            TeacherTeamGetIdentificationDataVO identificationDataByUserId = teacherTeamIdentificationService.getIdentificationDataByUserId(userId);
            return Result.ok(identificationDataByUserId);
        }
        else if(identification.equals("schoolFriend")){
            SchoolFriendGetIdentificationDataVO identificationDataByUserId = schoolFriendIdentificationService.getIdentificationDataByUserId(userId);
            return Result.ok(identificationDataByUserId);
        }
        else if(identification.equals("enterprise")){
            EnterpriseGetIdentificationDataVO identificationDataByUserId = enterpriseIdentificationService.getIdentificationDataByUserId(userId);
            return Result.ok(identificationDataByUserId);
        }
        return Result.fail("未知错误");
    }

    @Override
    public Result notifyAdminToAudit(String identification) {
        Long userId = UserHolder.getUser().getId();
        String key = "waitForAuditingList:";
        DeserializeUserIdAndIdentificationInRedisListDTO dto = new DeserializeUserIdAndIdentificationInRedisListDTO();
        dto.setIdentification(identification)
                .setUserId(userId);
        String value = JSONUtil.toJsonStr(dto);
        stringRedisTemplate.opsForList().rightPush(key, value);
        return Result.ok();

    }

    @Override
    public Result passIdentificationDataRequest(Long userId, String identification) {
        LambdaUpdateWrapper<Identification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Identification::getUserId, userId);
        if(identification.equals("student")){
            updateWrapper.set(Identification::getIsStudent, 3);
        }
        else if(identification.equals("teacher")){
            updateWrapper.set(Identification::getIsTeacher, 3);
        }
        else if(identification.equals("schoolFriend")){
            updateWrapper.set(Identification::getIsSchoolFriend, 3);
        }
        else if(identification.equals("enterprise")){
            updateWrapper.set(Identification::getIsEnterprise, 3);
        }
        boolean success = update(updateWrapper);
        if(success){
            return Result.ok();
        }
        return Result.fail("更新失败");
    }

    @Override
    public Result refuseIdentificationDataRequest(Long userId, String identification) {
        LambdaUpdateWrapper<Identification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Identification::getUserId, userId);
        if(identification.equals("student")){
            updateWrapper.set(Identification::getIsStudent, 2);
        }
        else if(identification.equals("teacher")){
            updateWrapper.set(Identification::getIsTeacher, 2);
        }
        else if(identification.equals("schoolFriend")){
            updateWrapper.set(Identification::getIsSchoolFriend, 2);
        }
        else if(identification.equals("enterprise")){
            updateWrapper.set(Identification::getIsEnterprise, 2);
        }
        boolean success = update(updateWrapper);
        if(success){
            return Result.ok();
        }
        return Result.fail("更新失败");
    }
}
