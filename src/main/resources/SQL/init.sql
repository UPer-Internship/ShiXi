/*
 Navicat MySQL Dump SQL

 Source Server         : dev01
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : rm-bp1t7lrt59wy71ty8xo.mysql.rds.aliyuncs.com:3306
 Source Schema         : 4_uper_up_intern

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 27/08/2025 22:59:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application`  (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `student_id` bigint NOT NULL COMMENT '学生ID',
                                `enterprise_id` bigint NOT NULL COMMENT '企业用户ID',
                                `job_id` bigint NOT NULL COMMENT '岗位ID',
                                `resume_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '简历文件URL',
                                `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '附加信息',
                                `status` enum('pending','accepted','rejected') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'pending' COMMENT '申请状态',
                                `is_read` tinyint(1) NULL DEFAULT 0 COMMENT '是否已读（0:未读 1:已读）',
                                `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除（0:未删除 1:已删除）',
                                `apply_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
                                `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`  (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                            `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '企业名称',
                            `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '企业Logo图片地址',
                            `industry` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属行业',
                            `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业简介',
                            `location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业所在城市+区',
                            `contact_person` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人',
                            `contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
                            `contact_email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系邮箱',
                            `website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业官网',
                            `scale` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业规模',
                            `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业类型',
                            `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '企业状态：0-禁用，1-正常，2-待审核',
                            `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '企业表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact`  (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `user_id` bigint NOT NULL COMMENT '发起者ID',
                            `contact_user_id` bigint NOT NULL COMMENT '被添加的联系人ID',
                            `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '联系人备注信息',
                            `last_contact_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后联系时间',
                            `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
                            `is_read` tinyint(1) NULL DEFAULT 0 COMMENT '是否已读（0:未读 1:已读）',
                            `contact_type` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT 'PERSONAL' COMMENT '联系人类型：WORK-工作好友，PERSONAL-普通好友',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE INDEX `uk_user_contact`(`user_id` ASC, `contact_user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for current_identification
-- ----------------------------
DROP TABLE IF EXISTS `current_identification`;
CREATE TABLE `current_identification`  (
                                           `id` bigint NOT NULL AUTO_INCREMENT,
                                           `user_id` bigint NOT NULL,
                                           `current_identification` tinyint(1) NULL DEFAULT 0 COMMENT '当前的身份 1：学生 2 ：校友 3：老师 4：企业',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for enterprise_identification
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_identification`;
CREATE TABLE `enterprise_identification`  (
                                              `id` bigint NOT NULL AUTO_INCREMENT,
                                              `user_id` bigint NOT NULL COMMENT '用户ID',
                                              `enterprise_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '企业名称',
                                              `enterprise_scale` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '企业规模',
                                              `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
                                              `your_position` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '你的职位',
                                              `picture_material_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '营业执照图片路径',
                                              `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '申请人邮箱',
                                              `gender` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '申请人性别',
                                              `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '申请人真实姓名',
                                              `enterprise_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '企业地址',
                                              `enterprise_industry` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '企业所属行业',
                                              `business_license_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '营业执照编号',
                                              PRIMARY KEY (`id`) USING BTREE,
                                              INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '企业身份认证表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for identification
-- ----------------------------
DROP TABLE IF EXISTS `identification`;
CREATE TABLE `identification`  (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `user_id` bigint NOT NULL,
                                   `is_student` tinyint(1) NULL DEFAULT 0,
                                   `is_school_friend` tinyint(1) NULL DEFAULT 0,
                                   `is_teacher` tinyint(1) NULL DEFAULT 0,
                                   `is_enterprise` tinyint(1) NULL DEFAULT 0,
                                   `is_admin` tinyint(1) NULL DEFAULT 0,
                                   `is_student_team` tinyint(1) NULL DEFAULT 0,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for industry
-- ----------------------------
DROP TABLE IF EXISTS `industry`;
CREATE TABLE `industry`  (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `industry_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                             `first_level_category_label` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                             `second_level_category_label` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 99 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job`  (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                        `publisher_id` bigint NOT NULL,
                        `company_id` bigint NULL DEFAULT NULL COMMENT '企业ID，逻辑外键',
                        `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职位标题',
                        `salary_min` double NULL DEFAULT NULL COMMENT '薪水下限',
                        `salary_max` double NULL DEFAULT NULL COMMENT '薪水上限',
                        `frequency` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '4天/周',
                        `total_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '4个月',
                        `onboard_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '到岗时间（如一周内、一个月内等）',
                        `enterprise_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '外企/校友企业',
                        `publisher` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'hr名字',
                        `enterprise_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业名称',
                        `financing_progress` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '融资阶段',
                        `enterprise_scale` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业规模',
                        `work_location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工作地点',
                        `detailed_information` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '职位详情信息',
                        `category` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职位类别',
                        `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职位类型',
                        `tag` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '岗位标签，使用-分割，如线下-可转正',
                        `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
                        `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '岗位状态：0-可申请，1-已截止',
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '职位表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for job_favorite
-- ----------------------------
DROP TABLE IF EXISTS `job_favorite`;
CREATE TABLE `job_favorite`  (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                 `user_id` bigint NOT NULL COMMENT '用户ID',
                                 `job_id` bigint NOT NULL COMMENT '岗位ID',
                                 `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 INDEX `idx_user_job`(`user_id` ASC, `job_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '岗位收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `major_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '底单专业名字 不可细分\r\n',
                          `first_level_category_label` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '专业的一级分类',
                          `second_level_category_label` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '专业的二级分类',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 248 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `sender_id` bigint NOT NULL,
                            `receiver_id` bigint NOT NULL,
                            `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `send_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
                            `is_read` tinyint(1) NULL DEFAULT 0 COMMENT '是否已读（0:未读 1:已读）',
                            `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除（0:未删除 1:已删除）',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1953817177067618307 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for options
-- ----------------------------
DROP TABLE IF EXISTS `options`;
CREATE TABLE `options`  (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `option_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                            `option_status` tinyint(1) NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region`  (
                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
                           `province` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '省份',
                           `city` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '城市\r\n',
                           `district` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '区县',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18672 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for resume_experience
-- ----------------------------
DROP TABLE IF EXISTS `resume_experience`;
CREATE TABLE `resume_experience`  (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '经历ID',
                                      `user_id` bigint NOT NULL COMMENT '所属学生基本信息的ID',
                                      `work_and_internship_experiences` json NOT NULL COMMENT '工作实习经历',
                                      `project_experiences` json NULL COMMENT '项目经历',
                                      `resume_link` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '软件附件链接（oss）',
                                      `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
                                      `identification` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      `education_experiences` json NULL,
                                      `advantages` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      `expected_position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      `birth_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      `wechat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '在线简历经历表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for school_friend_identification
-- ----------------------------
DROP TABLE IF EXISTS `school_friend_identification`;
CREATE TABLE `school_friend_identification`  (
                                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                                 `user_id` bigint NOT NULL COMMENT '用户ID',
                                                 `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '姓名',
                                                 `gender` enum('男','女','未知') CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '性别',
                                                 `birth_date` date NULL DEFAULT NULL COMMENT '出生日期',
                                                 `education_level` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '学历',
                                                 `university` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '学校名称',
                                                 `major` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '专业名称',
                                                 `enrollment_date` date NULL DEFAULT NULL COMMENT '入学时间',
                                                 `graduation_date` date NULL DEFAULT NULL COMMENT '毕业时间',
                                                 `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
                                                 `picture_material_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片路径',
                                                 `graduation_certificate_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '毕业证书编号',
                                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '校友身份认证表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for student_identification
-- ----------------------------
DROP TABLE IF EXISTS `student_identification`;
CREATE TABLE `student_identification`  (
                                           `id` bigint NOT NULL AUTO_INCREMENT,
                                           `user_id` bigint NOT NULL,
                                           `picture_material_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '',
                                           `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                           `gender` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                           `birth_date` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                           `education_level` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                           `university` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                           `major` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                           `enrollment_date` varbinary(255) NULL DEFAULT NULL,
                                           `graduation_date` varbinary(255) NULL DEFAULT NULL,
                                           `student_card_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for student_team_identification
-- ----------------------------
DROP TABLE IF EXISTS `student_team_identification`;
CREATE TABLE `student_team_identification`  (
                                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                                `user_id` bigint NOT NULL COMMENT '直接关联的用户id（负责人id）',
                                                `team_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '团队名称',
                                                `university_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学校名称',
                                                `school_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学院名称',
                                                `team_leader_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '团队负责人名称',
                                                `team_leader_gender` tinyint NULL DEFAULT NULL COMMENT '团队负责人性别 0-女 1-男',
                                                `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '入驻基地的地址',
                                                `identification_images` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '团队身份认证图片',
                                                `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-未删除 1-已删除',
                                                PRIMARY KEY (`id`) USING BTREE,
                                                INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生团队身份认证表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `perm_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '权限名称',
                                   `perm_code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '权限编码(如user:list)',
                                   `perm_type` tinyint NULL DEFAULT NULL COMMENT '权限类型(1=菜单,2=按钮,3=接口)',
                                   `url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '接口路径',
                                   `method` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '请求方法(GET/POST等)',
                                   `parent_id` bigint NULL DEFAULT NULL COMMENT '父权限ID',
                                   `sort` int NULL DEFAULT 0 COMMENT '排序号',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `perm_code`(`perm_code` ASC) USING BTREE,
                                   INDEX `parent_id`(`parent_id` ASC) USING BTREE,
                                   CONSTRAINT `sys_permission_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `sys_permission` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `role_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '角色名称',
                             `role_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '角色编码(如ROLE_ADMIN)',
                             `description` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色描述',
                             `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `role_name`(`role_name` ASC) USING BTREE,
                             UNIQUE INDEX `role_code`(`role_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
                                        `id` bigint NOT NULL AUTO_INCREMENT,
                                        `role_id` bigint NOT NULL COMMENT '角色ID',
                                        `perm_id` bigint NOT NULL COMMENT '权限ID',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE INDEX `uk_role_perm`(`role_id` ASC, `perm_id` ASC) USING BTREE,
                                        INDEX `perm_id`(`perm_id` ASC) USING BTREE,
                                        CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
                                        CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`perm_id`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色-权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `user_id` bigint NOT NULL COMMENT '用户ID',
                                  `role_id` bigint NOT NULL COMMENT '角色ID',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户-角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for teacher_identification
-- ----------------------------
DROP TABLE IF EXISTS `teacher_identification`;
CREATE TABLE `teacher_identification`  (
                                           `id` bigint NOT NULL AUTO_INCREMENT,
                                           `user_id` bigint NOT NULL COMMENT '用户ID',
                                           `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '教师姓名',
                                           `college` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '学院名称',
                                           `picture_material_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '教职工证',
                                           `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
                                           `major` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                           `gender` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '性别',
                                           `university` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '学校名称',
                                           `join_date` date NULL DEFAULT NULL COMMENT '入职时间',
                                           `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '邮箱地址',
                                           `work_certificate_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                           PRIMARY KEY (`id`) USING BTREE,
                                           INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '教师身份认证表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team`  (
                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                         `uuid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '团队的唯一标识',
                         `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '团队名称',
                         `description` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '团队描述',
                         `leader_id` bigint NOT NULL COMMENT '团队创建者ID，外键到用户表',
                         `school` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '团队所属院校',
                         `industry` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '团队所属行业',
                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
                         PRIMARY KEY (`id`) USING BTREE,
                         INDEX `leader_id`(`leader_id` ASC) USING BTREE,
                         INDEX `uuid`(`uuid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '团队信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for team_member
-- ----------------------------
DROP TABLE IF EXISTS `team_member`;
CREATE TABLE `team_member`  (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                `team_id` bigint NOT NULL COMMENT '团队ID，外键到团队表',
                                `user_id` bigint NOT NULL COMMENT '用户ID，外键到用户表',
                                `role` enum('leader','member') CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '成员角色，leader表示团队负责人，member表示普通成员',
                                `responsibility` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '成员职责描述',
                                `apply_reason` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '申请说明',
                                `join_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
                                `quit_time` datetime NULL DEFAULT NULL COMMENT '退出时间',
                                `status` enum('pending','approved','rejected') CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '申请状态，pending表示待审核，approved表示审核通过，rejected表示审核拒绝',
                                `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
                                PRIMARY KEY (`id`) USING BTREE,
                                INDEX `team_id`(`team_id` ASC) USING BTREE,
                                INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '团队成员关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for university
-- ----------------------------
DROP TABLE IF EXISTS `university`;
CREATE TABLE `university`  (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `university_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                               `designation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '985 211 双一流 一本 二本 。。。。\r\n',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                         `uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户唯一标识符',
                         `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话号码',
                         `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信开放id',
                         `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
                         `nick_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
                         `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '头像',
                         `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
                         `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名\r\n',
                         `birth_date` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生日',
                         `wechat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信',
                         `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
                         PRIMARY KEY (`id`) USING BTREE,
                         INDEX `uuid`(`uuid` ASC) USING BTREE,
                         INDEX `phone`(`phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_enterprise_info
-- ----------------------------
DROP TABLE IF EXISTS `user_enterprise_info`;
CREATE TABLE `user_enterprise_info`  (
                                         `id` bigint NOT NULL,
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_school_friend_info
-- ----------------------------
DROP TABLE IF EXISTS `user_school_friend_info`;
CREATE TABLE `user_school_friend_info`  (
                                            `id` bigint NOT NULL,
                                            `user_id` bigint NOT NULL,
                                            `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
                                            PRIMARY KEY (`id` DESC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_student_info
-- ----------------------------
DROP TABLE IF EXISTS `user_student_info`;
CREATE TABLE `user_student_info`  (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                      `user_id` bigint NOT NULL COMMENT '用户ID, 外键',
                                      `school_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学校名称',
                                      `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业名称',
                                      `graduation_date` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '毕业时间，格式：YYYY-MM-DD',
                                      `education_level` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学历（本科、大专等）',
                                      `advantages` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '个人优势',
                                      `expected_position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '期望职位，例如：互联网-产品实习生',
                                      `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
                                      PRIMARY KEY (`id`) USING BTREE,
                                      INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '在线简历表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_teacher_info
-- ----------------------------
DROP TABLE IF EXISTS `user_teacher_info`;
CREATE TABLE `user_teacher_info`  (
                                      `id` bigint NOT NULL,
                                      `user_id` bigint NOT NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

create table if not exists `4_uper_up_intern`.job_full_time
(
    id           bigint auto_increment
        primary key,
    publisher_id int           null comment '发布者id
',
    company_id   int           null comment '公司id',
    title        varchar(255)  null comment '岗位标题',
    salary_min   float         null comment '薪水下线',
    salary_max   float         null comment '薪水上限',
    main_text    varchar(2000) null comment '正文',
    province     varchar(255)  null comment '公司所在省份',
    city         varchar(255)  null comment '公司所在城市',
    district     varchar(255)  null comment '公司所在区',
    type         varchar(255)  null comment '工作类型 实习 兼职 正职',
    tag          varchar(255)  null comment 'tag',
    status       tinyint       null comment 'status',
    is_deleted   tinyint       null comment '逻辑删除',
    category     varchar(255)  null comment '岗位类别',
    label        varchar(255)  null comment '标签',
    salary_round int           null comment '年发薪次数'
);

create table if not exists `4_uper_up_intern`.job_internship
(
    id           int auto_increment
        primary key,
    publisher_id int           null comment '发布者id',
    company_id   int           null comment '公司id',
    title        varchar(255)  null comment '岗位标题',
    salary_min   float         null comment '薪水下线',
    salary_max   float         null comment '薪水上限',
    main_text    varchar(2000) null comment '正文',
    province     varchar(255)  null comment '公司所在省份',
    city         varchar(255)  null comment '公司所在城市',
    district     varchar(255)  null comment '公司所在区',
    type         varchar(255)  null comment '工作类型 实习 兼职 正职',
    tag          varchar(255)  null comment 'tag',
    status       tinyint       null comment 'status',
    is_deleted   tinyint       null comment '逻辑删除',
    category     varchar(255)  null comment '岗位类别',
    label        varchar(255)  null comment '标签'
);

create table if not exists `4_uper_up_intern`.job_part_time
(
    id           int auto_increment
        primary key,
    publisher_id int           null comment '发布者id',
    company_id   int           null comment '公司id',
    title        varchar(255)  null comment '岗位标题',
    salary_min   float         null comment '薪水下线',
    salary_max   float         null comment '薪水上限',
    main_text    varchar(2000) null comment '正文',
    province     varchar(255)  null comment '公司所在省份',
    city         varchar(255)  null comment '公司所在城市',
    district     varchar(255)  null comment '公司所在区',
    type         varchar(255)  null comment '工作类型 实习 兼职 正职',
    tag          varchar(255)  null comment 'tag',
    status       tinyint       null comment 'status',
    is_deleted   tinyint       null comment '逻辑删除',
    category     varchar(255)  null comment '岗位类别',
    label        varchar(255)  null comment '标签'
);

CREATE TABLE `position_favorite`
(
    `id`          bigint     NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`     bigint     NOT NULL COMMENT '用户ID',
    `position_id`      bigint     NOT NULL COMMENT '岗位ID',
    `type` varchar(20) NOT NULL COMMENT '岗位类型：正职、兼职、实习',
    `create_time` datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `is_deleted`  tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除标志，0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_user_position` (`user_id`, `position_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='新的岗位收藏表，对应position模块'

SET FOREIGN_KEY_CHECKS = 1;
