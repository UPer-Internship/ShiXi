DROP DATABASE IF EXISTS shixi;
CREATE DATABASE shixi;
USE shixi;
-- 用户表
CREATE TABLE `user`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `phone`      VARCHAR(20)  NOT NULL COMMENT '电话号码',
    `password`   VARCHAR(255)  COMMENT '密码',
    `nick_name`   VARCHAR(100) NOT NULL COMMENT '昵称',
    `icon`        VARCHAR(255) DEFAULT '' COMMENT '头像',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- 插入测试用户
#INSERT INTO `user` (`phone`, `password`, `nick_name`, `icon`)
#VALUES ('18378059289', 'password', 'admin', 'https://**');
# -- 学生基本信息表
# CREATE TABLE student_basic_info
# (
#     id                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '学生基本信息 ID',
#     user_id           BIGINT       NOT NULL COMMENT '与user的id绑定',
#     name              VARCHAR(255) NOT NULL COMMENT '姓名',
#     gender            VARCHAR(10) COMMENT '性别，例如：男 / 女',
#     birth_date        VARCHAR(10) COMMENT '出生年月，格式为 yyyy/MM，例如：2003/10',
#     highest_education VARCHAR(50) COMMENT '最高学历，例如：本科',
#     school_name       VARCHAR(255) COMMENT '学校名称，例如：华南理工大学',
#     major             VARCHAR(255) COMMENT '专业名称，例如：计算机科学与技术',
#     study_period      VARCHAR(50) COMMENT '就读时间',
#     expected_position VARCHAR(255) COMMENT '期望职位，例如：互联网-产品实习生',
#     create_time       DATETIME COMMENT '创建时间',
#     update_time       DATETIME COMMENT '更新时间',
#     INDEX idx_user_id (user_id)
# ) ENGINE = InnoDB
#   DEFAULT CHARSET = utf8mb4 COMMENT ='学生基本信息表';
# -- 在线简历表
# CREATE TABLE online_resume
# (
#     id              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '简历ID',
#     user_id      BIGINT                                        NOT NULL COMMENT '用户ID,外键',
#     name            VARCHAR(100)                                  NOT NULL COMMENT '姓名',
#     icon            VARCHAR(255) DEFAULT '' COMMENT '用户头像路径，默认空字符串', -- 考虑到简历可能要用证件照，这里不用student表里面的
#     school_name     VARCHAR(255)                                  NOT NULL COMMENT '学校名称',
#     major           VARCHAR(255)                                  NOT NULL COMMENT '专业名称',
#     graduation_date DATE COMMENT '毕业时间，格式：YYYY-MM-DD',
#     age             INT COMMENT '年龄',
#     phone           VARCHAR(20) COMMENT '联系电话',
#     wechat          VARCHAR(50) COMMENT '微信号',
#     education_level ENUM ('本科', '大专', '硕士', '博士', '其他') NOT NULL COMMENT '学历（本科、大专等）',
#     advantages      TEXT COMMENT '个人优势，长文本',
#     create_time     DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
#     update_time     DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
# ) ENGINE = InnoDB
#   DEFAULT CHARSET = utf8mb4 COMMENT ='在线简历表';
-- 在线简历表
CREATE TABLE student_info
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    user_id           BIGINT UNIQUE NOT NULL COMMENT '用户ID, 外键',
    name              VARCHAR(255) NOT NULL COMMENT '姓名',
    gender            VARCHAR(10)  COMMENT '性别，例如：男 / 女',
    phone             VARCHAR(20)  NOT NULL COMMENT '联系电话',
    birth_date        VARCHAR(10)  COMMENT '出生年月，格式为 yyyy/MM，例如：2003/10',
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
    update_time     DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='在线简历经历表';


DROP TABLE IF EXISTS `job`;
CREATE TABLE `job`
(
    `id`                   bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `publisher_id`         bigint                                                        NOT NULL,
    `title`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职位标题',
    `salary`               varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '薪水',
    `frequency`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '4天/周',
    `total_time`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '4个月',
    `enterprise_type`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '外企/校友企业',
    `publisher`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'hr名字',
    `enterprise_name`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业名称',
    `financing_progress`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '融资阶段',
    `enterprise_scale`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '企业规模',
    `work_location`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工作地点',
    `detailed_information` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         NULL COMMENT '职位详情信息',
    `category`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职位类别',
    `type`                 varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '职位类型',
    `create_time`          datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`          datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 17
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '职位表'
  ROW_FORMAT = Dynamic;

-- 消息表
CREATE TABLE message (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  sender_id BIGINT NOT NULL,
  receiver_id BIGINT NOT NULL,
  content TEXT NOT NULL,
  send_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (sender_id) REFERENCES user(id),
  FOREIGN KEY (receiver_id) REFERENCES user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 联系人表
CREATE TABLE contact (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     user_id BIGINT NOT NULL COMMENT '发起者ID',
     contact_user_id BIGINT NOT NULL COMMENT '被添加的联系人ID',
     last_contact_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后联系时间',
     is_blocked BOOLEAN DEFAULT FALSE COMMENT '是否屏蔽该联系人',
     UNIQUE KEY uk_user_contact (user_id, contact_user_id),
     FOREIGN KEY (user_id) REFERENCES user(id),
     FOREIGN KEY (contact_user_id) REFERENCES user(id)
) ENGINE = InnoDB;

SET FOREIGN_KEY_CHECKS = 1;