package com.ShiXi.user.IdentityAuthentication.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.IdentificationMapper;
import com.ShiXi.common.utils.RedissonLockUtil;
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
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.domin.vo.TeacherGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.service.TeacherIdentificationService;
import com.ShiXi.user.IdentityAuthentication.studentTeamIdentification.service.StudentTeamIdentificationService;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.service.EnterpriseIdentificationService;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.service.SchoolFriendIdentificationService;
import com.ShiXi.user.common.domin.dto.UserDTO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

import static com.ShiXi.common.utils.RedisConstants.LOGIN_USER_KEY;
import static com.ShiXi.user.IdentityAuthentication.common.utils.RedisConstants.*;

@Slf4j
@Service
public class IdentificationServiceImpl extends ServiceImpl<IdentificationMapper, Identification> implements IdentificationService {
    @Resource
    StudentIdentificationService studentIdentificationService;
   
    @Resource
    TeacherIdentificationService teacherIdentificationService;
   
    @Resource
    StudentTeamIdentificationService studentTeamIdentificationService;
   
    @Resource
    EnterpriseIdentificationService enterpriseIdentificationService;
   
    @Resource
    SchoolFriendIdentificationService schoolFriendIdentificationService;

    @Resource
    CurrentIdentificationService currentIdentificationService;;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    RedissonLockUtil redissonLockUtil;

    @Override
    public Result getAllIdentificationStatus() {
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

    //TODO 异步逻辑尚为完成
    //TODO 复用抽象尚未完成
    @Override
    public Result changeIdentification(Integer identification) {
        Long userId = UserHolder.getUser().getId();
        String token = LOGIN_USER_KEY + UserHolder.getUser().getToken();
        String userDTOJson = stringRedisTemplate.opsForValue().get(token);
        if(userDTOJson==null){
            return Result.fail("请先登录");
        }
        UserDTO userDTO = JSONUtil.toBean(userDTOJson, UserDTO.class);
        Identification identificationStatus = lambdaQuery().eq(Identification::getUserId, userId).one();
        if(identification==null){
            Result.fail("请选择身份");
        }
        else if (identification.equals(1)) {
            if(identificationStatus.getIsStudent()==3){
                userDTO.setIdentification(1);
                stringRedisTemplate.opsForValue().set(token, JSONUtil.toJsonStr(userDTO));
                currentIdentificationService.lambdaUpdate().eq(CurrentIdentification::getUserId, userId)
                        .set(CurrentIdentification::getCurrentIdentification, 1)
                        .update();
                return Result.ok();
            }
            else return Result.fail("此身份未验证");
        }
        else if (identification.equals(2)) {
            if(identificationStatus.getIsSchoolFriend()==3){
                userDTO.setIdentification(2);
                stringRedisTemplate.opsForValue().set(token, JSONUtil.toJsonStr(userDTO));
                currentIdentificationService.lambdaUpdate().eq(CurrentIdentification::getUserId, userId)
                        .set(CurrentIdentification::getCurrentIdentification, 2)
                        .update();
                return Result.ok();
            }
            else return Result.fail("此身份未验证");
        }
        else if (identification.equals(3)) {
            if(identificationStatus.getIsTeacher()==3){
                userDTO.setIdentification(3);
                stringRedisTemplate.opsForValue().set(token, JSONUtil.toJsonStr(userDTO));
                currentIdentificationService.lambdaUpdate().eq(CurrentIdentification::getUserId, userId)
                        .set(CurrentIdentification::getCurrentIdentification, 3)
                        .update();
                return Result.ok();
            }
            else return Result.fail("此身份未验证");
        }
        else if (identification.equals(4)) {
            if(identificationStatus.getIsEnterprise()==3){
                userDTO.setIdentification(4);
                stringRedisTemplate.opsForValue().set(token, JSONUtil.toJsonStr(userDTO));
                currentIdentificationService.lambdaUpdate().eq(CurrentIdentification::getUserId, userId)
                        .set(CurrentIdentification::getCurrentIdentification, 4)
                        .update();
                    return Result.ok();
            }
            else return Result.fail("此身份未验证");
        }
//        else if (identification.equals("studentTeam")) {
//            if(identificationStatus.getIsStudentTeam()==3){
//                userDTO.setIdentification(5);
//                stringRedisTemplate.opsForValue().set(token, JSONUtil.toJsonStr(userDTO));
//                currentIdentificationService.lambdaUpdate().eq(CurrentIdentification::getUserId, userId)
//                        .set(CurrentIdentification::getCurrentIdentification, 5)
//                        .update();
//                    return Result.ok();
//            }
//            else return Result.fail("此身份未验证");
//        }
        else if (identification.equals(6)) {
            if(identificationStatus.getIsAdmin()==3){
                userDTO.setIdentification(6);
                stringRedisTemplate.opsForValue().set(token, JSONUtil.toJsonStr(userDTO));
                currentIdentificationService.lambdaUpdate().eq(CurrentIdentification::getUserId, userId)
                        .set(CurrentIdentification::getCurrentIdentification, 6)
                        .update();
                return Result.ok();
            }
            else return Result.fail("此身份未验证");
        }
        return  Result.fail("未知错误");
    }

    @Override
    public Integer getCurrentIdentification() {
        Integer currentIdentificationCache = UserHolder.getUser().getIdentification();
        if(currentIdentificationCache==null){
            CurrentIdentification currentIdentification = currentIdentificationService.lambdaQuery()
                    .eq(CurrentIdentification::getUserId, UserHolder.getUser().getId())
                    .one();
            return currentIdentification.getCurrentIdentification();
        }
        return currentIdentificationCache;
    }

    @Override
    public Result getIdentificationDataRequest() {
        //产看审核人员自己的审核列表是否还有没完成的工作
        //有
        Long userId = UserHolder.getUser().getId();
        String key=ADMIN_AUDITING_BUFFER_POOL+userId;
        String undone = stringRedisTemplate.opsForValue().get(key);
        if(undone!=null){
            DeserializeUserIdAndIdentificationInRedisListDTO dto = JSONUtil.toBean(undone, DeserializeUserIdAndIdentificationInRedisListDTO.class);
            Long waitingForAuditingUserId = dto.getUserId();
            Integer identification = dto.getIdentification();
            if(identification.equals(1)){
                StudentGetIdentificationDataVO identificationDataByUserId = studentIdentificationService.getIdentificationDataByUserId(waitingForAuditingUserId);
                return Result.ok(identificationDataByUserId);
            }
            else if(identification.equals(2)){
                SchoolFriendGetIdentificationDataVO identificationDataByUserId =schoolFriendIdentificationService.getIdentificationDataByUserId(waitingForAuditingUserId);
                return Result.ok(identificationDataByUserId);
            }
            else if(identification.equals(3)){
                TeacherGetIdentificationDataVO identificationDataByUserId = teacherIdentificationService.getIdentificationDataByUserId(waitingForAuditingUserId);
                return Result.ok(identificationDataByUserId);
            }
            else if(identification.equals(4)){
                EnterpriseGetIdentificationDataVO identificationDataByUserId = enterpriseIdentificationService.getIdentificationDataByUserId(waitingForAuditingUserId);
                return Result.ok(identificationDataByUserId);
            }
            return Result.fail("未知错误");
        }
        //没有
        //加锁获取待审核列表
        boolean isLock = redissonLockUtil.tryLock(WAIT_FOR_AUDITING_LIST_LOCK, 3, 10, TimeUnit.SECONDS);
        if(!isLock){
            //加锁失败
            return Result.fail("请稍后再试");
        }
        //加锁成功
        //取出一个等待审核表单
        String waitForAuditingUserJsonStr = stringRedisTemplate.opsForList().leftPop(WAIT_FOR_AUDITING_LIST);
        if(waitForAuditingUserJsonStr==null){
            return Result.ok("暂无需要审核的资料");
        }
        //加入自己的缓存工作区
        stringRedisTemplate.opsForValue().set(key, waitForAuditingUserJsonStr);
        //释放锁
        redissonLockUtil.unlock(WAIT_FOR_AUDITING_LIST_LOCK);
        //反序列化后进行处理
        DeserializeUserIdAndIdentificationInRedisListDTO dto = JSONUtil.toBean(waitForAuditingUserJsonStr, DeserializeUserIdAndIdentificationInRedisListDTO.class);
        Long waitingForAuditingUserId = dto.getUserId();
        Integer identification = dto.getIdentification();
        //将缓存工作区加入一个按时间排列的zset，与定时任务配合
        stringRedisTemplate.opsForZSet().add(ADMIN_AUDITING_BUFFER_POOL_ZSET,key, System.currentTimeMillis());
        if(identification.equals(1)){
            StudentGetIdentificationDataVO identificationDataByUserId = studentIdentificationService.getIdentificationDataByUserId(waitingForAuditingUserId);
            return Result.ok(identificationDataByUserId);
        }
        else if(identification.equals(2)){
            SchoolFriendGetIdentificationDataVO identificationDataByUserId =schoolFriendIdentificationService.getIdentificationDataByUserId(waitingForAuditingUserId);
            return Result.ok(identificationDataByUserId);
        }
        else if(identification.equals(3)){
             TeacherGetIdentificationDataVO identificationDataByUserId = teacherIdentificationService.getIdentificationDataByUserId(waitingForAuditingUserId);
            return Result.ok(identificationDataByUserId);
        }
        else if(identification.equals(4)){
            EnterpriseGetIdentificationDataVO identificationDataByUserId = enterpriseIdentificationService.getIdentificationDataByUserId(waitingForAuditingUserId);
            return Result.ok(identificationDataByUserId);
        }
        return Result.fail("未知错误");
    }

    @Override
    public void notifyAdminToAudit(Integer identification) {
        Long userId = UserHolder.getUser().getId();
        DeserializeUserIdAndIdentificationInRedisListDTO dto = new DeserializeUserIdAndIdentificationInRedisListDTO();
        dto.setIdentification(identification)
                .setUserId(userId);
        String value = JSONUtil.toJsonStr(dto);
        stringRedisTemplate.opsForList().rightPush(WAIT_FOR_AUDITING_LIST, value);


    }

    @Override
    public Result passIdentificationDataRequest() {
        Long userId = UserHolder.getUser().getId();
        String key=ADMIN_AUDITING_BUFFER_POOL+userId;
        String waitForAuditingUserJsonStr = stringRedisTemplate.opsForValue().get(key);
        if(waitForAuditingUserJsonStr==null){
            return Result.fail("请勿重复操作");
        }
        DeserializeUserIdAndIdentificationInRedisListDTO dto = JSONUtil.toBean(waitForAuditingUserJsonStr, DeserializeUserIdAndIdentificationInRedisListDTO.class);
        Long waitingForAuditingUserId = dto.getUserId();
        Integer identification = dto.getIdentification();
        LambdaUpdateWrapper<Identification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Identification::getUserId, waitingForAuditingUserId);
        if(identification.equals(1)){
            updateWrapper.set(Identification::getIsStudent, 3);
        }
        else if(identification.equals(2)){
            updateWrapper.set(Identification::getIsSchoolFriend, 3);
        }
        else if(identification.equals(3)){
            updateWrapper.set(Identification::getIsTeacher, 3);
        }
        else if(identification.equals(4)){
            updateWrapper.set(Identification::getIsEnterprise, 3);
        }
        else if(identification.equals(6)){
            updateWrapper.set(Identification::getIsAdmin, 3);
        }
        boolean success = update(updateWrapper);
        if(success){
            //清空缓冲区
            stringRedisTemplate.delete(key);
            //删除zset中对应的记录
            stringRedisTemplate.opsForZSet().remove(ADMIN_AUDITING_BUFFER_POOL_ZSET, key);
            return Result.ok();
        }
        return Result.fail("更新失败");
    }

    @Override
    public Result refuseIdentificationDataRequest() {
        Long userId = UserHolder.getUser().getId();
        String key=ADMIN_AUDITING_BUFFER_POOL+userId;
        String waitForAuditingUserJsonStr = stringRedisTemplate.opsForValue().get(key);
        if(waitForAuditingUserJsonStr==null){
            return Result.fail("请勿重复操作");
        }
        DeserializeUserIdAndIdentificationInRedisListDTO dto = JSONUtil.toBean(waitForAuditingUserJsonStr, DeserializeUserIdAndIdentificationInRedisListDTO.class);
        Long waitingForAuditingUserId = dto.getUserId();
        Integer identification = dto.getIdentification();
        LambdaUpdateWrapper<Identification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Identification::getUserId, waitingForAuditingUserId);
        if(identification.equals(1)){
            updateWrapper.set(Identification::getIsStudent, 2);
        }
        else if(identification.equals(2)){
            updateWrapper.set(Identification::getIsSchoolFriend, 2);
        }
        else if(identification.equals(3)){
            updateWrapper.set(Identification::getIsTeacher, 2);
        }
        else if(identification.equals(4)){
            updateWrapper.set(Identification::getIsEnterprise, 2);
        }
        else if(identification.equals(6)){
            updateWrapper.set(Identification::getIsAdmin, 2);
        }
        boolean success = update(updateWrapper);
        if(success){
            //清空缓冲区
            stringRedisTemplate.delete(key);
            //删除zset中对应的记录
            stringRedisTemplate.opsForZSet().remove(ADMIN_AUDITING_BUFFER_POOL_ZSET, key);
            return Result.ok();
        }
        return Result.fail("更新失败");
    }

    @Override
    public Result getSpecifiedIdentificationStatus(Integer identification) {
        Long id = UserHolder.getUser().getId();
        Identification status = lambdaQuery()
                .eq(Identification::getUserId, id)  // 直接引用实体类的userId字段
                .one();
        if(identification==1){
           return Result.ok(status.getIsStudent());
        }
        else if(identification==2){
            return Result.ok(status.getIsSchoolFriend());
        }
        else if(identification==3){
            return Result.ok(status.getIsTeacher());
        }
        else if(identification==4){
            return Result.ok(status.getIsEnterprise());
        }
        else if(identification==6){
            return Result.ok(status.getIsAdmin());
        }
        return Result.fail("未知错误");
    }


}
