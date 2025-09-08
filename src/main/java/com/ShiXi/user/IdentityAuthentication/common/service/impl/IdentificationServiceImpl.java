package com.ShiXi.user.IdentityAuthentication.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.entity.University;
import com.ShiXi.common.mapper.IdentificationMapper;
import com.ShiXi.common.service.UniversityService;
import com.ShiXi.common.utils.RedissonLockUtil;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.IdentityAuthentication.common.domin.dto.DeserializeUserIdAndIdentificationInRedisListDTO;
import com.ShiXi.user.IdentityAuthentication.common.domin.dto.KeyAndWaitForAuditingUserDTO;
import com.ShiXi.user.IdentityAuthentication.common.domin.vo.IdentificationVO;
import com.ShiXi.user.IdentityAuthentication.common.entity.CurrentIdentification;
import com.ShiXi.user.IdentityAuthentication.common.entity.Identification;
import com.ShiXi.user.IdentityAuthentication.common.service.CurrentIdentificationService;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.domin.vo.EnterpriseGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.domin.vo.EnterpriseIdentificationForAuditingDataVO;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.entity.EnterpriseIdentification;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.domin.vo.SchoolFriendGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.domin.vo.SchoolFriendIdentificationForAuditingDataVO;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.entity.SchoolFriendIdentification;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.domin.vo.StudentGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.domin.vo.StudentIdentificationForAuditingDataVO;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.entity.StudentIdentification;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.service.StudentIdentificationService;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.domin.vo.TeacherGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.domin.vo.TeacherIdentificationForAuditingDataVO;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.entity.TeacherIdentification;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.service.TeacherIdentificationService;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.service.EnterpriseIdentificationService;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.service.SchoolFriendIdentificationService;
import com.ShiXi.user.common.domin.dto.UserDTO;
import com.ShiXi.user.info.studentInfo.entity.StudentInfo;
import com.ShiXi.user.info.studentInfo.service.StudentInfoService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    UniversityService universityService;
   
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

    @Resource
    StudentInfoService studentInfoService;
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
        //查看审核人员自己的审核列表是否还有没完成的工作
        //有
        Long userId = UserHolder.getUser().getId();
        String key=ADMIN_AUDITING_BUFFER_POOL+userId;
        String undone = stringRedisTemplate.opsForValue().get(key);
        if(undone!=null){
            DeserializeUserIdAndIdentificationInRedisListDTO dto = JSONUtil.toBean(undone, DeserializeUserIdAndIdentificationInRedisListDTO.class);
            Long waitingForAuditingUserId = dto.getUserId();
            Integer identification = dto.getIdentification();
            return processIdentificationData(identification, waitingForAuditingUserId, false);
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
        KeyAndWaitForAuditingUserDTO keyAndWaitForAuditingUserDTO = new KeyAndWaitForAuditingUserDTO();
        keyAndWaitForAuditingUserDTO.setKey(key)
            .setWaitingForAuditingUserId(waitingForAuditingUserId)
            .setIdentification(identification);
        stringRedisTemplate.opsForZSet().add(ADMIN_AUDITING_BUFFER_POOL_ZSET,JSONUtil.toJsonStr(keyAndWaitForAuditingUserDTO), System.currentTimeMillis());
        return processIdentificationData(identification, waitingForAuditingUserId, true);
    }

    /**
     * 处理身份认证数据请求的通用方法
     * @param identification 身份类型
     * @param waitingForAuditingUserId 待审核用户ID
     * @param isNew 是否是新获取的数据
     * @return Result
     */
    private Result processIdentificationData(Integer identification, Long waitingForAuditingUserId, boolean isNew) {
        if(identification.equals(1)){
            //获取学生信息
            StudentGetIdentificationDataVO identificationDataByUserId = studentIdentificationService.getIdentificationDataByUserId(waitingForAuditingUserId);
            //获取近似的已认证的学生信息
            List<StudentIdentification> similarStudentIdentifications = studentIdentificationService
                    .lambdaQuery()
                    .eq(StudentIdentification::getStudentCardNumber, identificationDataByUserId.getStudentCardNumber())
                    .eq(StudentIdentification::getUniversity, identificationDataByUserId.getUniversity())
                    .ne(StudentIdentification::getUserId, waitingForAuditingUserId)
                    .list();
            StudentIdentificationForAuditingDataVO studentIdentificationForAuditingDataVO = BeanUtil.copyProperties(identificationDataByUserId,StudentIdentificationForAuditingDataVO.class);
            if(similarStudentIdentifications!=null){
                studentIdentificationForAuditingDataVO.setSuspected(true);
                studentIdentificationForAuditingDataVO.setSuspectedUserIds(similarStudentIdentifications
                        .stream()
                        .map(StudentIdentification::getUserId)
                        .collect(Collectors.toList()));
            }
            return Result.ok(studentIdentificationForAuditingDataVO);
        }
        else if(identification.equals(2)){
            //获取校友信息
            SchoolFriendGetIdentificationDataVO identificationDataByUserId =schoolFriendIdentificationService.getIdentificationDataByUserId(waitingForAuditingUserId);
            //获取近似的已认证的校友信息
            List<SchoolFriendIdentification> similarSchoolFriendIdentifications = schoolFriendIdentificationService
                    .lambdaQuery()
                    .eq(SchoolFriendIdentification::getGraduationCertificateNumber, identificationDataByUserId.getGraduationCertificateNumber())
                    .eq(SchoolFriendIdentification::getUniversity, identificationDataByUserId.getUniversity())
                    .ne(SchoolFriendIdentification::getUserId, waitingForAuditingUserId)
                    .list();
            SchoolFriendIdentificationForAuditingDataVO schoolFriendIdentificationForAuditingDataVO = BeanUtil.copyProperties(identificationDataByUserId, SchoolFriendIdentificationForAuditingDataVO.class);
            if(similarSchoolFriendIdentifications!=null){
                schoolFriendIdentificationForAuditingDataVO.setSuspected(true);
                schoolFriendIdentificationForAuditingDataVO.setSuspectedUserIds(similarSchoolFriendIdentifications
                        .stream()
                        .map(SchoolFriendIdentification::getUserId)
                        .collect(Collectors.toList()));
            }
            return Result.ok(schoolFriendIdentificationForAuditingDataVO);
        }
        else if(identification.equals(3)){
             //获取教师信息
             TeacherGetIdentificationDataVO identificationDataByUserId = teacherIdentificationService.getIdentificationDataByUserId(waitingForAuditingUserId);
             //获取近似的已认证的教师信息
             List<TeacherIdentification>  similarTeacherIdentifications= teacherIdentificationService
                     .lambdaQuery()
                     .eq(TeacherIdentification::getWorkCertificateNumber, identificationDataByUserId.getWorkCertificateNumber())
                     .eq(TeacherIdentification::getUniversity, identificationDataByUserId.getUniversity())
                     .ne(TeacherIdentification::getUserId, waitingForAuditingUserId)
                     .list();
             TeacherIdentificationForAuditingDataVO teacherIdentificationForAuditingDataVO = BeanUtil.copyProperties(identificationDataByUserId, TeacherIdentificationForAuditingDataVO.class);
             if(similarTeacherIdentifications!=null){
                 teacherIdentificationForAuditingDataVO.setSuspected(true);
                 teacherIdentificationForAuditingDataVO.setSuspectedUserIds(similarTeacherIdentifications
                         .stream()
                         .map(TeacherIdentification::getUserId)
                         .collect(Collectors.toList()));
             }
             return Result.ok(teacherIdentificationForAuditingDataVO);
        }
        else if(identification.equals(4)){
            //获取企业信息
            EnterpriseGetIdentificationDataVO identificationDataByUserId = enterpriseIdentificationService.getIdentificationDataByUserId(waitingForAuditingUserId);
            //获取近似的已认证的企业信息
            List<EnterpriseIdentification> similarEnterpriseIdentification = enterpriseIdentificationService
                    .lambdaQuery()
                    .eq(EnterpriseIdentification::getBusinessLicenseNumber, identificationDataByUserId.getBusinessLicenseNumber())
                    .eq(EnterpriseIdentification::getEnterpriseName, identificationDataByUserId.getEnterpriseName())
                    .ne(EnterpriseIdentification::getUserId, waitingForAuditingUserId)
                    .list();
            EnterpriseIdentificationForAuditingDataVO enterpriseIdentificationForAuditingDataVO = BeanUtil.copyProperties(identificationDataByUserId, EnterpriseIdentificationForAuditingDataVO.class);
            if(similarEnterpriseIdentification!=null){
                enterpriseIdentificationForAuditingDataVO.setSuspected(true);
                enterpriseIdentificationForAuditingDataVO.setSuspectedUserIds(similarEnterpriseIdentification
                        .stream()
                        .map(EnterpriseIdentification::getUserId)
                        .collect(Collectors.toList()));
            }
            return Result.ok(enterpriseIdentificationForAuditingDataVO);
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
            StudentGetIdentificationDataVO identificationDataByUserId = studentIdentificationService.getIdentificationDataByUserId(waitingForAuditingUserId);
            University university = universityService.lambdaQuery().eq(University::getUniversity, identificationDataByUserId.getUniversity()).one();
            studentInfoService.lambdaUpdate()
                    .eq(StudentInfo::getUserId, identificationDataByUserId.getUserId())
                    .set(StudentInfo::getSchoolName,identificationDataByUserId.getUniversity())
                    .set(StudentInfo::getMajor,identificationDataByUserId.getMajor())
                    .set(StudentInfo::getGraduationDate,identificationDataByUserId.getGraduationDate())
                    .set(StudentInfo::getEducationLevel,identificationDataByUserId.getEducationLevel())
                    .set(StudentInfo::getEnrollmentDate,identificationDataByUserId.getEnrollmentDate())
                    .set(StudentInfo::getTags,university.getDesignation())
                    .update();
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
    public Result refuseIdentificationDataRequest(String reason) {
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
            studentIdentificationService.lambdaUpdate()
                    .eq(StudentIdentification::getUserId, waitingForAuditingUserId)
                    .set(StudentIdentification::getFeedback, reason)
                    .update();
        }
        else if(identification.equals(2)){
            updateWrapper.set(Identification::getIsSchoolFriend, 2);
            schoolFriendIdentificationService.lambdaUpdate()
                    .eq(SchoolFriendIdentification::getUserId, waitingForAuditingUserId)
                    .set(SchoolFriendIdentification::getFeedback, reason)
                    .update();
        }
        else if(identification.equals(3)){
            updateWrapper.set(Identification::getIsTeacher, 2);
            teacherIdentificationService.lambdaUpdate()
                    .eq(TeacherIdentification::getUserId, waitingForAuditingUserId)
                    .set(TeacherIdentification::getFeedback, reason)
                    .update();
        }
        else if(identification.equals(4)){
            updateWrapper.set(Identification::getIsEnterprise, 2);
            enterpriseIdentificationService.lambdaUpdate()
                    .eq(EnterpriseIdentification::getUserId, waitingForAuditingUserId)
                    .set(EnterpriseIdentification::getFeedback, reason)
                    .update();
        }
        else if(identification.equals(6)){
            updateWrapper.set(Identification::getIsAdmin, 2);
        }
        boolean success = update(updateWrapper);
        if(success){
            //清空缓冲区
            stringRedisTemplate.delete(key);
            //删除zset中对应的记录
            KeyAndWaitForAuditingUserDTO keyAndWaitForAuditingUser = new KeyAndWaitForAuditingUserDTO();
            keyAndWaitForAuditingUser.setIdentification( identification)
                    .setWaitingForAuditingUserId(waitingForAuditingUserId)
                    .setKey(key);
            String ZSetKey = JSONUtil.toJsonStr(keyAndWaitForAuditingUser);
            stringRedisTemplate.opsForZSet().remove(ADMIN_AUDITING_BUFFER_POOL_ZSET, ZSetKey);
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

    @Override
    public Result getRefusedReason(Integer identification) {
        Long id = UserHolder.getUser().getId();
        if(identification==1){
            StudentIdentification status = studentIdentificationService.lambdaQuery()
                    .eq(StudentIdentification::getUserId, id)  // 直接引用实体类的userId字段
                    .one();
            return Result.ok(status.getFeedback());
        }
        else if(identification==2){
            SchoolFriendIdentification status = schoolFriendIdentificationService.lambdaQuery()
                    .eq(SchoolFriendIdentification::getUserId, id)  // 直接引用实体类的userId字段
                    .one();
            return Result.ok(status.getFeedback());
        }
        else if(identification==3){
            TeacherIdentification status = teacherIdentificationService.lambdaQuery()
                    .eq(TeacherIdentification::getUserId, id)  // 直接引用实体类的userId字段
                    .one();
            return Result.ok(status.getFeedback());
        }
        else if(identification==4){
            EnterpriseIdentification status = enterpriseIdentificationService.lambdaQuery()
                    .eq(EnterpriseIdentification::getUserId, id)  // 直接引用实体类的userId字段
                    .one();
            return Result.ok(status.getFeedback());
        }

        return Result.fail("未知错误");
    }

    @Override
    public Result getIdentificationDataByUserId(Integer userId,Integer identification) {
        if(identification==1){
            StudentIdentification studentIdentification = studentIdentificationService.lambdaQuery()
                    .eq(StudentIdentification::getUserId, userId)  // 直接引用实体类的userId字段
                    .one();
            StudentGetIdentificationDataVO studentGetIdentificationDataVO = new StudentGetIdentificationDataVO();
            BeanUtil.copyProperties(studentIdentification, studentGetIdentificationDataVO);
            return Result.ok(studentGetIdentificationDataVO);
        }
        else if(identification==2){
            SchoolFriendIdentification schoolFriendIdentification = schoolFriendIdentificationService.lambdaQuery()
                    .eq(SchoolFriendIdentification::getUserId, userId)  // 直接引用实体类的userId字段
                    .one();
            SchoolFriendGetIdentificationDataVO schoolFriendGetIdentificationDataVO = new SchoolFriendGetIdentificationDataVO();
            BeanUtil.copyProperties(schoolFriendIdentification, schoolFriendGetIdentificationDataVO);
            return Result.ok(schoolFriendGetIdentificationDataVO);
        }
        else if(identification==3){

            TeacherIdentification teacherIdentification = teacherIdentificationService.lambdaQuery()
                    .eq(TeacherIdentification::getUserId, userId)  // 直接引用实体类的userId字段
                    .one();
                    TeacherGetIdentificationDataVO teacherGetIdentificationDataVO = new TeacherGetIdentificationDataVO();
                    BeanUtil.copyProperties(teacherIdentification, teacherGetIdentificationDataVO);
                    return Result.ok(teacherGetIdentificationDataVO);
        }
        else if(identification==4){

            EnterpriseIdentification enterpriseIdentification = enterpriseIdentificationService.lambdaQuery()
                    .eq(EnterpriseIdentification::getUserId, userId)  // 直接引用实体类的userId字段
                    .one();
            EnterpriseGetIdentificationDataVO enterpriseGetIdentificationDataVO = new EnterpriseGetIdentificationDataVO();
            BeanUtil.copyProperties(enterpriseIdentification, enterpriseGetIdentificationDataVO);
            return Result.ok(enterpriseGetIdentificationDataVO);
        }
        return Result.fail("未知错误");
    }


}
