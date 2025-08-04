DROP DATABASE IF EXISTS shixi;
CREATE DATABASE shixi;
USE shixi;
-- 用户表
CREATE TABLE `user`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `phone`       VARCHAR(20)  NOT NULL COMMENT '电话号码',
    `openid`      VARCHAR(255) COMMENT '微信开放id',
    `password`    VARCHAR(255) COMMENT '密码',
    `nick_name`   VARCHAR(100) NOT NULL COMMENT '昵称',
    `icon`        VARCHAR(255) DEFAULT '' COMMENT '头像',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`   TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- 在线简历表
CREATE TABLE student_info
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    user_id           BIGINT UNIQUE NOT NULL COMMENT '用户ID, 外键',
    name              VARCHAR(255)  NOT NULL COMMENT '姓名',
    gender            VARCHAR(10) COMMENT '性别，例如：男 / 女',
    phone             VARCHAR(20)   NOT NULL COMMENT '联系电话',
    birth_date        VARCHAR(10) COMMENT '出生年月，格式为 yyyy/MM，例如：2003/10',
    school_name       VARCHAR(255) COMMENT '学校名称',
    major             VARCHAR(255) COMMENT '专业名称',
    icon              VARCHAR(255) DEFAULT '' COMMENT '用户头像路径，默认空字符串',
    graduation_date   DATE COMMENT '毕业时间，格式：YYYY-MM-DD',
    wechat            VARCHAR(50) COMMENT '微信号',
    education_level   ENUM ('本科', '大专', '硕士', '博士', '其他') COMMENT '学历（本科、大专等）',
    advantages        TEXT COMMENT '个人优势',
    expected_position VARCHAR(255) COMMENT '期望职位，例如：互联网-产品实习生',
    create_time       DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time       DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted        TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
    INDEX idx_user_id (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '在线简历表';

-- 在线简历经历表
CREATE TABLE resume_experience
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '经历ID',
    student_info_id BIGINT                                  NOT NULL COMMENT '所属学生基本信息的ID',
    type            ENUM ('工作', '实习', '项目', '作品集') NOT NULL COMMENT '经历类型（工作、实习、项目、作品集）',
    description     TEXT COMMENT '经历描述，长文本',
    link            VARCHAR(512) DEFAULT NULL COMMENT '可选的链接，允许为空',
    create_time     DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted      TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='在线简历经历表';

-- 企业表
CREATE TABLE company (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '企业名称',
    logo VARCHAR(255) DEFAULT '' COMMENT '企业Logo图片地址',
    industry VARCHAR(50) DEFAULT NULL COMMENT '所属行业',
    description VARCHAR(255) DEFAULT NULL COMMENT '企业简介',
    location VARCHAR(100) DEFAULT NULL COMMENT '企业所在城市+区',
    contact_person VARCHAR(20) DEFAULT NULL COMMENT '联系人',
    contact_phone VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    contact_email VARCHAR(50) DEFAULT NULL COMMENT '联系邮箱',
    website VARCHAR(255) DEFAULT NULL COMMENT '企业官网',
    scale VARCHAR(50) DEFAULT NULL COMMENT '企业规模',
    type VARCHAR(50) DEFAULT NULL COMMENT '企业类型',
    status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '企业状态：0-禁用，1-正常，2-待审核',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业表';

-- 职位表
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job`
(
    `id`                   bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `publisher_id`         bigint                                                        NOT NULL COMMENT '发布者ID，逻辑外键到user表',
    `company_id`           bigint                                                        NULL DEFAULT NULL COMMENT '企业ID，逻辑外键',
    `title`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职位标题',
    `salary_min` double NULL DEFAULT NULL COMMENT '薪水下限',
    `salary_max` double NULL DEFAULT NULL COMMENT '薪水上限',
    `frequency`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '4天/周',
    `total_time`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '实习总时长',
    `onboard_time`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '到岗时间（如一周内、一个月内等）',
    `enterprise_type`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '外企/校友企业',
    `publisher`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'hr名字',
    `enterprise_name`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业名称',
    `financing_progress`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '融资阶段',
    `enterprise_scale`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '企业规模',
    `work_location`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工作地点',
    `detailed_information` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         NULL COMMENT '职位详情信息',
    `category`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职位类别',
    `type`                 varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '职位类型',
    `tag`                  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '岗位标签，使用-分割，如线下-可转正',
    `create_time`          datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`          datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`           TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
    `status`              TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '岗位状态：0-可申请，1-已截止',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 17
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '职位表'
  ROW_FORMAT = Dynamic;
-- 岗位收藏表
CREATE TABLE job_favorite (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    job_id BIGINT NOT NULL COMMENT '岗位ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0-未删除，1-已删除',
    KEY idx_user_job (user_id, job_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位收藏表';

-- 消息表
CREATE TABLE message (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  sender_id BIGINT NOT NULL,
  receiver_id BIGINT NOT NULL,
  content TEXT NOT NULL,
  send_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  is_read TINYINT(1) DEFAULT 0 COMMENT '是否已读（0:未读 1:已读）',
  is_deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除（0:未删除 1:已删除）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 联系人表
CREATE TABLE contact (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     user_id BIGINT NOT NULL COMMENT '发起者ID',
     contact_user_id BIGINT NOT NULL COMMENT '被添加的联系人ID',
     remark VARCHAR(255) COMMENT '联系人备注信息',
     contact_type VARCHAR(20) DEFAULT 'PERSONAL' COMMENT '联系人类型：WORK-工作好友，PERSONAL-普通好友',
     last_contact_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后联系时间',
     is_deleted TINYINT(1) DEFAULT FALSE COMMENT '是否屏蔽该联系人',
     is_read TINYINT(1) DEFAULT FALSE COMMENT '是否已读（0:未读 1:已读）',
     UNIQUE KEY uk_user_contact (user_id, contact_user_id)
) ENGINE = InnoDB;

-- 申请表
CREATE TABLE application (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     student_id BIGINT NOT NULL COMMENT '学生ID',
     enterprise_id BIGINT NOT NULL COMMENT '企业用户ID',
     job_id BIGINT NOT NULL COMMENT '岗位ID',
     resume_url VARCHAR(255) NOT NULL COMMENT '简历文件URL',
     message TEXT COMMENT '附加信息',
     status ENUM('pending', 'accepted', 'rejected') DEFAULT 'pending' COMMENT '申请状态',
     is_read TINYINT(1) DEFAULT 0 COMMENT '是否已读（0:未读 1:已读）',
     is_deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除（0:未删除 1:已删除）',
     apply_time DATETIME DEFAULT CURRENT_TIMESTAMP,
     update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;