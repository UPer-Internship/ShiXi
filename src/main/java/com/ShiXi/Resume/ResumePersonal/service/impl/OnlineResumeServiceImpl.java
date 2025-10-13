package com.ShiXi.Resume.ResumePersonal.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.ShiXi.Resume.ResumePersonal.entity.ResumeLink;
import com.ShiXi.Resume.ResumePersonal.service.ResumeLinkService;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.domin.dto.PageResult;
import com.ShiXi.Resume.ResumePersonal.domin.dto.UpdateResumeDTO;
import com.ShiXi.Resume.ResumePersonal.domin.dto.ResumePageQueryDTO;
import com.ShiXi.Resume.ResumePersonal.domin.vo.ResumeVO;
import com.ShiXi.Resume.ResumePersonal.domin.vo.ResumePublicVO;
import com.ShiXi.Resume.ResumePersonal.entity.Resume;
import com.ShiXi.common.mapper.ResumeExperienceMapper;
import com.ShiXi.common.mapper.StudentInfoMapper;
import com.ShiXi.Resume.ResumePersonal.service.OnlineResumeService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.common.utils.OSSUtil;
import com.ShiXi.user.common.entity.User;
import com.ShiXi.user.common.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OnlineResumeServiceImpl extends ServiceImpl<ResumeExperienceMapper, Resume> implements OnlineResumeService {

    @Resource
    private OSSUtil ossUtil;

    @Resource
    private UserService userService;
    @Resource
    private ResumeLinkService resumeLinkService;
    // 简历附件存储目录前缀
    private static final String RESUME_ATTACHMENT_DIR = "resume/attachments/";




    @Override
    public Result getResumeByResumeId(Long resumeId){
        Resume resumeExperience = lambdaQuery()
                .eq(Resume::getId, resumeId)  // 直接引用实体类的userId字段
                .one();
        if(resumeExperience == null){
            return null;
        }
        ResumeVO resumeExperienceVO = new ResumeVO();
        resumeExperienceVO.setId(resumeExperience.getId())
                .setUserId(resumeExperience.getUserId())
                .setProjectExperiences(JSONUtil.toList(resumeExperience.getProjectExperiences(), ResumeVO.projectExperience.class))
                .setWorkAndInternshipExperiences(JSONUtil.toList(resumeExperience.getWorkAndInternshipExperiences(), ResumeVO.workAndInternshipExperience.class))
                .setEducationExperiences(JSONUtil.toList(resumeExperience.getEducationExperiences(),ResumeVO.educationExperience.class))
                .setGender(resumeExperience.getGender())
                .setBirthDate(resumeExperience.getBirthDate())
                .setName(resumeExperience.getName())
                .setPhone(resumeExperience.getPhone())
                .setWechat(resumeExperience.getWechat());
        return Result.ok(resumeExperienceVO);
    }

    @Override
    public Result getMyResume() {
        //获取用户id
        Long id = UserHolder.getUser().getId();
        //获取用户经历
        Resume resumeExperience = lambdaQuery()
                .eq(Resume::getUserId, id)  // 直接引用实体类的userId字段
                .one();
        if(resumeExperience==null){
            return Result.fail("此用户还没有上传简历");
        }
        //获取用户基本信息
        ResumeVO resumeExperienceVO = new ResumeVO();
        resumeExperienceVO.setId(resumeExperience.getId())
                .setUserId(resumeExperience.getUserId())
                .setProjectExperiences(JSONUtil.toList(resumeExperience.getProjectExperiences(), ResumeVO.projectExperience.class))
                .setWorkAndInternshipExperiences(JSONUtil.toList(resumeExperience.getWorkAndInternshipExperiences(), ResumeVO.workAndInternshipExperience.class))
                .setEducationExperiences(JSONUtil.toList(resumeExperience.getEducationExperiences(),ResumeVO.educationExperience.class))
                .setGender(resumeExperience.getGender())
                .setBirthDate(resumeExperience.getBirthDate())
                .setName(resumeExperience.getName())
                .setPhone(resumeExperience.getPhone())
                .setWechat(resumeExperience.getWechat())
                .setResumeLink(resumeExperienceVO.getResumeLink())
                .setAdvantages(resumeExperience.getAdvantages())
                .setExpectedPosition(JSONUtil.toList(resumeExperience.getExpectedPosition(),String.class));
        return Result.ok(resumeExperienceVO);
    }

    public Result updateMyExperience(UpdateResumeDTO reqDTO) {
        Long userId = UserHolder.getUser().getId();

        // 先查询该用户是否已有经验记录
        Resume experience = lambdaQuery()
                .eq(Resume::getUserId, userId)
                .one();

        if (experience != null) {
            // 有记录则更新
            boolean success = lambdaUpdate()
                    .eq(Resume::getUserId, userId)
                    .set(Resume::getWorkAndInternshipExperiences, JSONUtil.toJsonStr(reqDTO.getWorkAndInternshipExperiences()))
                    .set(Resume::getProjectExperiences, JSONUtil.toJsonStr(reqDTO.getProjectExperiences()))
                    .set(Resume::getResumeLink, reqDTO.getResumeLink())
                    .set(Resume::getEducationExperiences,JSONUtil.toJsonStr(reqDTO.getEducationExperiences()))
                    .set(Resume::getExpectedPosition,JSONUtil.toJsonStr(reqDTO.getExpectedPosition()))
                    .set(Resume::getAdvantages,reqDTO.getAdvantages())
                    .set(Resume::getBirthDate,reqDTO.getBirthDate())
                    .set(Resume::getName,reqDTO.getName())
                    .set(Resume::getPhone,reqDTO.getPhone())
                    .set(Resume::getWechat,reqDTO.getWechat())
                    .set(Resume::getGender,reqDTO.getGender())

                    .update();

            if (success) {
                return Result.ok();
            }
            return Result.fail("更新失败");
        } else {
            // 无记录则新建
            Resume newExperience = new Resume();
            newExperience.setUserId(userId);
            newExperience.setWorkAndInternshipExperiences(JSONUtil.toJsonStr(reqDTO.getWorkAndInternshipExperiences()));
            newExperience.setProjectExperiences(JSONUtil.toJsonStr(reqDTO.getProjectExperiences()));
            newExperience.setResumeLink(reqDTO.getResumeLink());
            newExperience.setEducationExperiences(JSONUtil.toJsonStr(reqDTO.getEducationExperiences()));
            newExperience.setExpectedPosition(JSONUtil.toJsonStr(reqDTO.getExpectedPosition()));
            newExperience.setAdvantages(reqDTO.getAdvantages());
            newExperience.setBirthDate(reqDTO.getBirthDate());
            newExperience.setName(reqDTO.getName());
            newExperience.setPhone(reqDTO.getPhone());
            newExperience.setWechat(reqDTO.getWechat());
            newExperience.setGender(reqDTO.getGender());
            boolean saveSuccess =save(newExperience);
            if (saveSuccess) {
                return Result.ok();
            }
            return Result.fail("创建记录失败");
        }
    }

    @Override
    public Result pageQueryPublicResumes(ResumePageQueryDTO resumePageQueryDTO) {
        // 处理分页参数默认值
        Integer page = resumePageQueryDTO.getPage() == null ? 1 : resumePageQueryDTO.getPage();
        Integer pageSize = resumePageQueryDTO.getPageSize() == null ? 10 : resumePageQueryDTO.getPageSize();

        // 构建查询条件
        QueryWrapper<Resume> wrapper = new QueryWrapper<>();
        
        // 如果有期望职位查询条件，进行模糊查询
        if (resumePageQueryDTO.getExpectedPosition() != null && 
            !resumePageQueryDTO.getExpectedPosition().trim().isEmpty()) {
            wrapper.like("expected_position", resumePageQueryDTO.getExpectedPosition());
        }
        
        // 按创建时间倒序排列
        wrapper.orderByDesc("create_time");

        // 执行分页查询
        Page<Resume> resumePage = new Page<>(page, pageSize);
        Page<Resume> resultPage = this.page(resumePage, wrapper);

        // 转换为公开信息VO
        List<ResumePublicVO> publicVOList = resultPage.getRecords().stream()
                .map(this::convertToPublicVO)
                .collect(Collectors.toList());

        // 构建分页结果
        PageResult pageResult = new PageResult(resultPage.getTotal(), publicVOList);
        return Result.ok(pageResult);
    }

    /**
     * 将Resume实体转换为ResumePublicVO
     */
    private ResumePublicVO convertToPublicVO(Resume resume) {
        ResumePublicVO publicVO = new ResumePublicVO();
        publicVO.setId(resume.getId());
        publicVO.setName(resume.getName());
        publicVO.setAdvantages(resume.getAdvantages());

        // 反序列化期望职位
        if (resume.getExpectedPosition() != null) {
            publicVO.setExpectedPosition(JSONUtil.toList(resume.getExpectedPosition(), String.class));
        }

        // 反序列化教育经历 - 复用ResumeVO中的静态内部类
        if (resume.getEducationExperiences() != null) {
            publicVO.setEducationExperiences(JSONUtil.toList(resume.getEducationExperiences(), 
                    ResumeVO.educationExperience.class));
        }

        // 反序列化工作实习经历 - 复用ResumeVO中的静态内部类
        if (resume.getWorkAndInternshipExperiences() != null) {
            publicVO.setWorkAndInternshipExperiences(JSONUtil.toList(resume.getWorkAndInternshipExperiences(), 
                    ResumeVO.workAndInternshipExperience.class));
        }

        // 反序列化项目经历 - 复用ResumeVO中的静态内部类
        if (resume.getProjectExperiences() != null) {
            publicVO.setProjectExperiences(JSONUtil.toList(resume.getProjectExperiences(), 
                    ResumeVO.projectExperience.class));
        }

        return publicVO;
    }

    @Override
    public Result getResumeByUserId(Long userId) {
        if (userId == null) {
            return Result.fail("用户ID不能为空");
        }
        
        Resume resumeExperience = lambdaQuery()
                .eq(Resume::getUserId, userId)
                .one();
        
        if (resumeExperience == null) {
            return Result.fail("该用户还没有上传简历");
        }
        
        ResumeVO resumeExperienceVO = new ResumeVO();
        resumeExperienceVO.setId(resumeExperience.getId())
                .setUserId(resumeExperience.getUserId())
                .setProjectExperiences(JSONUtil.toList(resumeExperience.getProjectExperiences(), ResumeVO.projectExperience.class))
                .setWorkAndInternshipExperiences(JSONUtil.toList(resumeExperience.getWorkAndInternshipExperiences(), ResumeVO.workAndInternshipExperience.class))
                .setEducationExperiences(JSONUtil.toList(resumeExperience.getEducationExperiences(), ResumeVO.educationExperience.class))
                .setGender(resumeExperience.getGender())
                .setBirthDate(resumeExperience.getBirthDate())
                .setName(resumeExperience.getName())
                .setPhone(resumeExperience.getPhone())
                .setWechat(resumeExperience.getWechat())
                .setResumeLink(resumeExperience.getResumeLink())
                .setAdvantages(resumeExperience.getAdvantages())
                .setExpectedPosition(JSONUtil.toList(resumeExperience.getExpectedPosition(), String.class));
        
        return Result.ok(resumeExperienceVO);
    }

    @Override
    public Result getResumeByUuid(String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            return Result.fail("用户UUID不能为空");
        }
        
        // 直接从user表根据UUID查询用户信息获取用户ID
        User user = userService.lambdaQuery().eq(User::getUuid, uuid).one();
        if (user == null) {
            return Result.fail("用户不存在");
        }
        
        Long userId = user.getId();
        
        // 调用已有的根据用户ID查询简历的方法
        return getResumeByUserId(userId);
    }

    @Override
    public Result uploadResumeAttachment(MultipartFile file) {
        try {
            // 参数校验
            if (file == null || file.isEmpty()) {
                return Result.fail("请选择要上传的文件");
            }

            // 检查文件类型（支持常见的简历文件格式）
            String originalFilename = file.getOriginalFilename();
            if (!isValidResumeFile(originalFilename)) {
                return Result.fail("只支持pdf、doc、docx格式的简历文件");
            }

            // 检查文件大小（限制为10MB）
            if (file.getSize() > 10 * 1024 * 1024) {
                return Result.fail("文件大小不能超过10MB");
            }

            // 获取当前用户ID
            Long userId = UserHolder.getUser().getId();
            if (userId == null) {
                return Result.fail("用户未登录");
            }

            // 上传到OSS
            String fileUrl = ossUtil.uploadAvatar(file, RESUME_ATTACHMENT_DIR);
            if (fileUrl == null) {
                return Result.fail("文件上传失败");
            }

            // 更新数据库中的简历附件链接
            boolean updateSuccess = lambdaUpdate()
                    .eq(Resume::getUserId, userId)
                    .set(Resume::getResumeLink, fileUrl)
                    .update();

            if (!updateSuccess) {
                // 如果更新失败，可能是用户还没有简历记录，创建一个新的
                Resume newResume = new Resume();
                newResume.setUserId(userId);
                newResume.setResumeLink(fileUrl);
                boolean saveSuccess = save(newResume);
                if (!saveSuccess) {
                    return Result.fail("保存简历附件信息失败");
                }
            }

            return Result.ok(fileUrl);

        } catch (IOException e) {
            log.error("上传简历附件失败", e);
            return Result.fail("文件上传失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("上传简历附件时发生未知错误", e);
            return Result.fail("上传失败，请稍后重试");
        }
    }

    @Override
    public Result getResumeAttachmentUrl(Long resumeId) {
        if (resumeId == null) {
            return Result.fail("简历ID不能为空");
        }

        Resume resume = lambdaQuery()
                .eq(Resume::getId, resumeId)
                .one();

        if (resume == null) {
            return Result.fail("简历不存在");
        }

        String resumeLink = resume.getResumeLink();
        if (resumeLink == null || resumeLink.trim().isEmpty()) {
            return Result.fail("该简历暂无附件");
        }

        return Result.ok(resumeLink);
    }

    @Override
    public Result uploadResumeAttachmentV2(MultipartFile file) {
        try {
            // 参数校验
            if (file == null || file.isEmpty()) {
                return Result.fail("请选择要上传的文件");
            }

            // 检查文件类型（支持常见的简历文件格式）
            String originalFilename = file.getOriginalFilename();
            if (!isValidResumeFile(originalFilename)) {
                return Result.fail("只支持pdf、doc、docx格式的简历文件");
            }

            // 检查文件大小（限制为10MB）
            if (file.getSize() > 10 * 1024 * 1024) {
                return Result.fail("文件大小不能超过10MB");
            }

            // 获取当前用户ID
            Long userId = UserHolder.getUser().getId();
            if (userId == null) {
                return Result.fail("用户未登录");
            }

            Resume resume = lambdaQuery().eq(Resume::getUserId, userId).one();

            Long resumeId = resume.getId();

            List<ResumeLink> ResumeLinkList = resumeLinkService.lambdaQuery().eq(ResumeLink::getResumeId, resumeId).list();

            if(ResumeLinkList.size()>=3){
                return Result.fail("最多允许上传三分简历");
            }
            // 上传到OSS
            String fileUrl = ossUtil.uploadAvatar(file, RESUME_ATTACHMENT_DIR);
            if (fileUrl == null) {
                return Result.fail("文件上传失败");
            }
            ResumeLink resumeLink = new ResumeLink();
            resumeLink.setResumeId(resumeId)
                    .setResumeLink(fileUrl)
                    .setUserId(userId);
            resumeLinkService.save(resumeLink);
            return Result.ok(fileUrl);

        } catch (IOException e) {
            log.error("上传简历附件失败", e);
            return Result.fail("文件上传失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("上传简历附件时发生未知错误", e);
            return Result.fail("上传失败，请稍后重试");
        }
    }

    @Override
    public Result deleteAttachment(Long attachmentId) {
        resumeLinkService.removeById(attachmentId);
        return Result.ok();
    }

    @Override
    public Result getAttachmentIds() {
        Long userId = UserHolder.getUser().getId();
        List<Long> ids = resumeLinkService.lambdaQuery()
                .eq(ResumeLink::getUserId, userId)
                .list()
                .stream()
                .map(ResumeLink::getId)
                .toList();
        return Result.ok(ids);
    }

    @Override
    public Result getAttachmentById(Long attachmentId) {
        ResumeLink attachment = resumeLinkService.lambdaQuery().eq(ResumeLink::getId, attachmentId).one();
        return Result.ok(attachment.getResumeLink());
    }

    /**
     * 检查是否为有效的简历文件格式
     */
    private boolean isValidResumeFile(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return false;
        }
        
        String lowerCaseFilename = filename.toLowerCase();
        return lowerCaseFilename.endsWith(".pdf") || 
               lowerCaseFilename.endsWith(".doc") || 
               lowerCaseFilename.endsWith(".docx");
    }
}
