SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application`
(
    `id`            bigint                                                                                  NOT NULL AUTO_INCREMENT,
    `student_id`    bigint                                                                                  NOT NULL COMMENT '学生ID',
    `enterprise_id` bigint                                                                                  NOT NULL COMMENT '企业用户ID',
    `job_id`        bigint                                                                                  NOT NULL COMMENT '岗位ID',
    `resume_url`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                           NOT NULL COMMENT '简历文件URL',
    `message`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                                   NULL COMMENT '附加信息',
    `status`        enum ('pending','accepted','rejected') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'pending' COMMENT '申请状态',
    `is_read`       tinyint(1)                                                                              NULL DEFAULT 0 COMMENT '是否已读（0:未读 1:已读）',
    `is_deleted`    tinyint(1)                                                                              NULL DEFAULT 0 COMMENT '是否删除（0:未删除 1:已删除）',
    `apply_time`    datetime                                                                                NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`   datetime                                                                                NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '申请表';

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`
(
    `id`             bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '企业名称',
    `logo`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT '' COMMENT '企业Logo图片地址',
    `industry`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '所属行业',
    `description`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '企业简介',
    `location`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '企业所在城市+区',
    `contact_person` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '联系人',
    `contact_phone`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '联系电话',
    `contact_email`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '联系邮箱',
    `website`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '企业官网',
    `scale`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '企业规模',
    `type`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '企业类型',
    `status`         tinyint(1)                                                    NOT NULL DEFAULT 1 COMMENT '企业状态：0-禁用，1-正常，2-待审核',
    `create_time`    datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`     tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '企业表';

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact`
(
    `id`                bigint                                                        NOT NULL AUTO_INCREMENT,
    `user_id`           bigint                                                        NOT NULL COMMENT '发起者ID',
    `contact_user_id`   bigint                                                        NOT NULL COMMENT '被添加的联系人ID',
    `remark`            varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '联系人备注信息',
    `last_contact_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后联系时间',
    `is_deleted`        tinyint(1)                                                    NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
    `is_read`           tinyint(1)                                                    NULL DEFAULT 0 COMMENT '是否已读（0:未读 1:已读）',
    `contact_type`      varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci  NULL DEFAULT 'PERSONAL' COMMENT '联系人类型：WORK-工作好友，PERSONAL-普通好友',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_user_contact` (`user_id` ASC, `contact_user_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci COMMENT = '联系人表';

-- ----------------------------
-- Table structure for current_identification
-- ----------------------------
DROP TABLE IF EXISTS `current_identification`;
CREATE TABLE `current_identification`
(
    `id`                     bigint     NOT NULL AUTO_INCREMENT,
    `user_id`                bigint     NOT NULL,
    `current_identification` tinyint(1) NULL DEFAULT 0 COMMENT '当前的身份 1：学生 2 ：校友 3：老师 4：企业',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci;

-- ----------------------------
-- Table structure for enterprise_identification
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_identification`;
CREATE TABLE `enterprise_identification`
(
    `id`                 bigint                                                        NOT NULL AUTO_INCREMENT,
    `user_id`            bigint                                                        NOT NULL COMMENT '用户ID',
    `enterprise_name`    varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT '' COMMENT '企业名称',
    `your_position`     varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT '' COMMENT '你的职位',
    `enterprise_scale`   varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT '' COMMENT '企业规模',
    `enterprise_industry` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT ''COMMENT '企业所属行业',
    `enterprise_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT '' COMMENT '企业地址',
    `name`                varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT '' COMMENT '申请人真实姓名',
    `gender`              varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT '' COMMENT '申请人性别',
    `email`               varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT '' COMMENT '申请人邮箱',
    `business_license`   varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT '' COMMENT '营业执照',
    `is_deleted`         tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `user_id` (`user_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci COMMENT = '企业身份认证表';

-- ----------------------------
-- Table structure for identification
-- ----------------------------
DROP TABLE IF EXISTS `identification`;
CREATE TABLE `identification`
(
    `id`               bigint     NOT NULL AUTO_INCREMENT,
    `user_id`          bigint     NOT NULL,
    `is_student`       tinyint(1) NULL DEFAULT 0,
    `is_school_friend` tinyint(1) NULL DEFAULT 0,
    `is_teacher`       tinyint(1) NULL DEFAULT 0,
    `is_enterprise`    tinyint(1) NULL DEFAULT 0,
    `is_admin`         tinyint(1) NULL DEFAULT 0,
    `is_student_team`  tinyint(1) NULL DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci;

-- ----------------------------
-- Table structure for industry
-- ----------------------------
DROP TABLE IF EXISTS `industry`;
CREATE TABLE `industry`
(
    `id`                          bigint                                                        NOT NULL AUTO_INCREMENT,
    `industry_name`               varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
    `first_level_category_label`  varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
    `second_level_category_label` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci;

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job`
(
    `id`                   bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `publisher_id`         bigint                                                        NOT NULL,
    `company_id`           bigint                                                        NULL     DEFAULT NULL COMMENT '企业ID，逻辑外键',
    `title`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '职位标题',
    `salary_min`           double                                                        NULL     DEFAULT NULL COMMENT '薪水下限',
    `salary_max`           double                                                        NULL     DEFAULT NULL COMMENT '薪水上限',
    `frequency`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '4天/周',
    `total_time`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '4个月',
    `onboard_time`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '到岗时间（如一周内、一个月内等）',
    `enterprise_type`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '外企/校友企业',
    `publisher`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT 'hr名字',
    `enterprise_name`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '企业名称',
    `financing_progress`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '融资阶段',
    `enterprise_scale`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '企业规模',
    `work_location`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '工作地点',
    `detailed_information` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         NULL COMMENT '职位详情信息',
    `category`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '职位类别',
    `type`                 varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '职位类型',
    `tag`                  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '岗位标签，使用-分割，如线下-可转正',
    `create_time`          datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`          datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`           tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
    `status`               tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '岗位状态：0-可申请，1-已截止',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '职位表';

-- ----------------------------
-- Table structure for job_favorite
-- ----------------------------
DROP TABLE IF EXISTS `job_favorite`;
CREATE TABLE `job_favorite`
(
    `id`          bigint     NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`     bigint     NOT NULL COMMENT '用户ID',
    `job_id`      bigint     NOT NULL COMMENT '岗位ID',
    `create_time` datetime   NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `is_deleted`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_user_job` (`user_id` ASC, `job_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '岗位收藏表';

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`
(
    `id`                          bigint                                                        NOT NULL AUTO_INCREMENT,
    `major_name`                  varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '底单专业名字 不可细分\r\n',
    `first_level_category_label`  varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '专业的一级分类',
    `second_level_category_label` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '专业的二级分类',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`
(
    `id`          bigint                                                NOT NULL AUTO_INCREMENT,
    `sender_id`   bigint                                                NOT NULL,
    `receiver_id` bigint                                                NOT NULL,
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `send_time`   datetime                                              NULL DEFAULT CURRENT_TIMESTAMP,
    `is_read`     tinyint(1)                                            NULL DEFAULT 0 COMMENT '是否已读（0:未读 1:已读）',
    `is_deleted`  tinyint(1)                                            NULL DEFAULT 0 COMMENT '是否删除（0:未删除 1:已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for options
-- ----------------------------
DROP TABLE IF EXISTS `options`;
CREATE TABLE `options`
(
    `id`            bigint                                                        NOT NULL AUTO_INCREMENT,
    `option_name`   varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
    `option_status` tinyint(1)                                                    NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci;

-- ----------------------------
-- Table structure for resume_experience
-- ----------------------------
DROP TABLE IF EXISTS `resume_experience`;
CREATE TABLE `resume_experience`
(
    `id`                              bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '经历ID',
    `user_id`                         bigint                                                        NOT NULL COMMENT '所属学生基本信息的ID',
    `work_and_internship_experiences` json                                                          NOT NULL COMMENT '工作实习经历',
    `project_experiences`             json                                                          NULL COMMENT '项目经历',
    `resume_link`                     varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '软件附件链接（oss）',
    `create_time`                     datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`                     datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`                      tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
    `identification`                  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL,
    `education_experiences`           json                                                          NULL,
    `advantages`                      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL,
    `expected_position`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL,
    `phone`                           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL,
    `gender`                          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL,
    `birth_date`                      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL,
    `name`                            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL,
    `wechat`                          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '在线简历经历表';


-- ----------------------------
-- Table structure for school_friend_identification
-- ----------------------------
DROP TABLE IF EXISTS `school_friend_identification`;
CREATE TABLE `school_friend_identification`
(
    `id`                     bigint                                                        NOT NULL AUTO_INCREMENT,
    `user_id`                bigint                                                        NOT NULL COMMENT '用户ID',
    `name`                   varchar(255)                                                  NULL     DEFAULT NULL COMMENT '姓名',
    `gender`                 ENUM ('男', '女', '未知')                                     NULL     DEFAULT NULL COMMENT '性别',
    `birth_date`             DATE                                                          NULL     DEFAULT NULL COMMENT '出生日期',
    `education`              VARCHAR(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT NULL COMMENT '学历',
    `school`                 varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT NULL COMMENT '学校名称',
    `major`                  varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT NULL COMMENT '专业名称',
    `begin_time`             DATE                                                          NULL     DEFAULT NULL COMMENT '入学时间',
    `graduation_time`        DATE                                                          NULL     DEFAULT NULL COMMENT '毕业时间',
    `graduation_certificate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT '' COMMENT '毕业证书',
    `is_deleted`             tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
    PRIMARY KEY (`id`) USING BTREE,
    KEY (`user_id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci COMMENT = '校友身份认证表';

-- ----------------------------
-- Table structure for student_identification
-- ----------------------------
DROP TABLE IF EXISTS `student_identification`;
CREATE TABLE `student_identification`
(
    `id`                   bigint                                                        NOT NULL AUTO_INCREMENT,
    `user_id`              bigint                                                        NOT NULL,
    `picture_material_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '',
    `name`                 varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
    `gender`               varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
    `birth_date`           varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
    `education_level`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
    `university`           varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
    `major`                varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
    `enrollment_date`      varbinary(255)                                                NULL DEFAULT NULL,
    `graduation_date`      varbinary(255)                                                NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci;

-- ----------------------------
-- Table structure for student_team_identification
-- ----------------------------
DROP TABLE IF EXISTS `student_team_identification`;
CREATE TABLE `student_team_identification`
(
    `id`                    bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_id`               bigint                                                        NOT NULL COMMENT '直接关联的用户id（负责人id）',
    `team_name`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '团队名称',
    `university_name`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '学校名称',
    `school_name`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '学院名称',
    `team_leader_name`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '团队负责人名称',
    `team_leader_gender`    tinyint                                                       NULL     DEFAULT NULL COMMENT '团队负责人性别 0-女 1-男',
    `address`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '入驻基地的地址',
    `identification_images` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         NULL COMMENT '团队身份认证图片',
    `is_deleted`            tinyint                                                       NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-未删除 1-已删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `user_id` (`user_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生团队身份认证表';

-- ----------------------------
-- Table structure for teacher_identification
-- ----------------------------
DROP TABLE IF EXISTS `teacher_identification`;
CREATE TABLE `teacher_identification`
(
    `id`               bigint                                                        NOT NULL AUTO_INCREMENT,
    `user_id`          bigint                                                        NOT NULL COMMENT '用户ID',
    `name`             varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT '' COMMENT '教师姓名',
    `nickname`         varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT '' COMMENT '教师昵称',
    `gender`           varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT '' COMMENT '性别',
    `birth_date`       DATE                                                          NULL     DEFAULT NULL COMMENT '出生日期',
    `university`       varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT '' COMMENT '学校名称',
    `school`           varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT '' COMMENT '学院名称',
    `join_date`        DATE                                                          NULL     DEFAULT NULL COMMENT '入职时间',
    `email`            varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT '' COMMENT '邮箱地址',
    `work_certificate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL     DEFAULT '' COMMENT '工作证明',
    `is_deleted`       tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `user_id` (`user_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci COMMENT = '教师身份认证表';

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uuid`        varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '团队的唯一标识',
    `name`        varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '团队名称',
    `description` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci         NULL COMMENT '团队描述',
    `leader_id`   bigint                                                        NOT NULL COMMENT '团队创建者ID，外键到用户表',
    `school`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '团队所属院校',
    `industry`    varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '团队所属行业',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint                                                       NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `leader_id` (`leader_id` ASC) USING BTREE,
    INDEX `uuid` (`uuid` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci COMMENT = '团队信息表';


-- ----------------------------
-- Table structure for team_member
-- ----------------------------
DROP TABLE IF EXISTS `team_member`;
CREATE TABLE `team_member`
(
    `id`        BIGINT                                   NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    `team_id`   BIGINT                                   NOT NULL COMMENT '团队ID，外键到团队表',
    `user_id`   BIGINT                                   NOT NULL COMMENT '用户ID，外键到用户表',
    `role`      ENUM ('leader', 'member')                COMMENT '成员角色，leader表示团队负责人，member表示普通成员',
    `responsibility` TEXT COMMENT '成员职责描述',
    `apply_reason` TEXT COMMENT '申请说明',
    `join_time` DATETIME                                 NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    `quit_time` DATETIME COMMENT '退出时间',
    `status`    ENUM ('pending', 'approved', 'rejected') NOT NULL COMMENT '申请状态，pending表示待审核，approved表示审核通过，rejected表示审核拒绝',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
    KEY (`team_id`),
    KEY (`user_id`)
)COMMENT '团队成员关系表';
CREATE TABLE sys_role_permission (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     role_id BIGINT NOT NULL COMMENT '角色ID',
                                     perm_id BIGINT NOT NULL COMMENT '权限ID',
                                     UNIQUE KEY uk_role_perm (role_id, perm_id), -- 避免重复关联
                                     FOREIGN KEY (role_id) REFERENCES sys_role(id) ON DELETE CASCADE,
                                     FOREIGN KEY (perm_id) REFERENCES sys_permission(id) ON DELETE CASCADE
) COMMENT '角色-权限关联表';

CREATE TABLE sys_user_role (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               user_id BIGINT NOT NULL COMMENT '用户ID',
                               role_id BIGINT NOT NULL COMMENT '角色ID',
                               UNIQUE KEY uk_user_role (user_id, role_id), -- 避免重复关联
                               FOREIGN KEY (user_id) REFERENCES sys_user(id) ON DELETE CASCADE,
                               FOREIGN KEY (role_id) REFERENCES sys_role(id) ON DELETE CASCADE
) COMMENT '用户-角色关联表';

CREATE TABLE sys_permission (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                perm_name VARCHAR(100) NOT NULL COMMENT '权限名称',
                                perm_code VARCHAR(100) NOT NULL UNIQUE COMMENT '权限编码(如user:list)',
                                perm_type TINYINT COMMENT '权限类型(1=菜单,2=按钮,3=接口)',
                                url VARCHAR(255) COMMENT '接口路径',
                                method VARCHAR(10) COMMENT '请求方法(GET/POST等)',
                                parent_id BIGINT COMMENT '父权限ID',
                                sort INT DEFAULT 0 COMMENT '排序号',
                                FOREIGN KEY (parent_id) REFERENCES sys_permission(id) ON DELETE SET NULL
) COMMENT '权限表';

CREATE TABLE sys_role (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          role_name VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',
                          role_code VARCHAR(50) NOT NULL UNIQUE COMMENT '角色编码(如ROLE_ADMIN)',
                          description VARCHAR(200) COMMENT '角色描述',
                          create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '角色表';