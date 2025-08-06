create table `4_uper_up_intern`.application
(
    id            bigint auto_increment
        primary key,
    student_id    bigint                                                             not null comment '学生ID',
    enterprise_id bigint                                                             not null comment '企业用户ID',
    job_id        bigint                                                             not null comment '岗位ID',
    resume_url    varchar(255)                                                       not null comment '简历文件URL',
    message       text                                                               null comment '附加信息',
    status        enum ('pending', 'accepted', 'rejected') default 'pending'         null comment '申请状态',
    is_read       tinyint(1)                               default 0                 null comment '是否已读（0:未读 1:已读）',
    is_deleted    tinyint(1)                               default 0                 null comment '是否删除（0:未删除 1:已删除）',
    apply_time    datetime                                 default CURRENT_TIMESTAMP null,
    update_time   datetime                                 default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
)
    charset = utf8mb4;

create table `4_uper_up_intern`.company
(
    id             bigint auto_increment comment '主键ID'
        primary key,
    name           varchar(100)                           not null comment '企业名称',
    logo           varchar(255) default ''                null comment '企业Logo图片地址',
    industry       varchar(50)                            null comment '所属行业',
    description    varchar(255)                           null comment '企业简介',
    location       varchar(100)                           null comment '企业所在城市+区',
    contact_person varchar(20)                            null comment '联系人',
    contact_phone  varchar(20)                            null comment '联系电话',
    contact_email  varchar(50)                            null comment '联系邮箱',
    website        varchar(255)                           null comment '企业官网',
    scale          varchar(50)                            null comment '企业规模',
    type           varchar(50)                            null comment '企业类型',
    status         tinyint(1)   default 1                 not null comment '企业状态：0-禁用，1-正常，2-待审核',
    create_time    datetime     default CURRENT_TIMESTAMP null comment '创建时间',
    update_time    datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted     tinyint(1)   default 0                 not null comment '逻辑删除标志，0-未删除，1-已删除'
)
    comment '企业表' charset = utf8mb4;

create table `4_uper_up_intern`.contact
(
    id                bigint auto_increment
        primary key,
    user_id           bigint                                not null comment '发起者ID',
    contact_user_id   bigint                                not null comment '被添加的联系人ID',
    remark            varchar(255)                          null comment '联系人备注信息',
    last_contact_time datetime    default CURRENT_TIMESTAMP null comment '最后联系时间',
    is_deleted        tinyint(1)  default 0                 null comment '逻辑删除标志，0-未删除，1-已删除',
    is_read           tinyint(1)  default 0                 null comment '是否已读（0:未读 1:已读）',
    contact_type      varchar(20) default 'PERSONAL'        null comment '联系人类型：WORK-工作好友，PERSONAL-普通好友',
    constraint uk_user_contact
        unique (user_id, contact_user_id)
);

create table `4_uper_up_intern`.identification
(
    id               bigint auto_increment
        primary key,
    user_id          bigint               not null,
    is_student       tinyint(1) default 0 null,
    is_school_friend tinyint(1) default 0 null,
    is_teacher       tinyint(1) default 0 null,
    is_enterprise    tinyint(1) default 0 null
);

create table `4_uper_up_intern`.job
(
    id                   bigint auto_increment comment '主键ID'
        primary key,
    publisher_id         bigint                               not null,
    company_id           bigint                               null comment '企业ID，逻辑外键',
    title                varchar(255)                         null comment '职位标题',
    salary_min           double                               null comment '薪水下限',
    salary_max           double                               null comment '薪水上限',
    frequency            varchar(50)                          null comment '4天/周',
    total_time           varchar(50)                          null comment '4个月',
    onboard_time         varchar(50)                          null comment '到岗时间（如一周内、一个月内等）',
    enterprise_type      varchar(100)                         null comment '外企/校友企业',
    publisher            varchar(100)                         null comment 'hr名字',
    enterprise_name      varchar(100)                         null comment '企业名称',
    financing_progress   varchar(50)                          null comment '融资阶段',
    enterprise_scale     varchar(50)                          null comment '企业规模',
    work_location        varchar(100)                         null comment '工作地点',
    detailed_information text                                 null comment '职位详情信息',
    category             varchar(100)                         null comment '职位类别',
    type                 varchar(50)                          null comment '职位类型',
    tag                  varchar(100)                         null comment '岗位标签，使用-分割，如线下-可转正',
    create_time          datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    update_time          datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted           tinyint(1) default 0                 not null comment '逻辑删除标志，0-未删除，1-已删除',
    status               tinyint(1) default 0                 not null comment '岗位状态：0-可申请，1-已截止'
)
    comment '职位表' charset = utf8mb4
                     row_format = DYNAMIC;

create table `4_uper_up_intern`.job_favorite
(
    id          bigint auto_increment comment '主键ID'
        primary key,
    user_id     bigint                               not null comment '用户ID',
    job_id      bigint                               not null comment '岗位ID',
    create_time datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    is_deleted  tinyint(1) default 0                 not null comment '逻辑删除标志，0-未删除，1-已删除'
)
    comment '岗位收藏表' charset = utf8mb4;

create index idx_user_job
    on `4_uper_up_intern`.job_favorite (user_id, job_id);

create table `4_uper_up_intern`.message
(
    id          bigint auto_increment
        primary key,
    sender_id   bigint                               not null,
    receiver_id bigint                               not null,
    content     text                                 not null,
    send_time   datetime   default CURRENT_TIMESTAMP null,
    is_read     tinyint(1) default 0                 null comment '是否已读（0:未读 1:已读）',
    is_deleted  tinyint(1) default 0                 null comment '是否删除（0:未删除 1:已删除）'
)
    charset = utf8mb4;

create table `4_uper_up_intern`.options
(
    id            bigint auto_increment
        primary key,
    option_name   varchar(255) null,
    option_status tinyint(1)   null
);

create table `4_uper_up_intern`.resume_experience
(
    id                              bigint auto_increment comment '经历ID'
        primary key,
    user_id                         bigint                               not null comment '所属学生基本信息的ID',
    work_and_internship_experiences json                                 not null comment '工作实习经历',
    project_experiences             json                                 null comment '项目经历',
    resume_link                     varchar(512)                         null comment '软件附件链接（oss）',
    create_time                     datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    update_time                     datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted                      tinyint(1) default 0                 not null comment '逻辑删除标志，0-未删除，1-已删除',
    identification                  varchar(20)                          null,
    education_experiences           json                                 null,
    advantages                      varchar(255)                         null,
    expected_position               varchar(255)                         null,
    phone                           varchar(255)                         null,
    gender                          varchar(255)                         null,
    birth_date                      varchar(255)                         null,
    name                            varchar(255)                         null,
    wechat                          varchar(255)                         null
)
    comment '在线简历经历表' charset = utf8mb4;

create table `4_uper_up_intern`.student_identification
(
    id                     bigint auto_increment
        primary key,
    user_id                bigint                  not null comment '用户ID',
    identity_card          varchar(255) default '' null,
    graduation_certificate varchar(255) default '' null,
    student_id_card        varchar(255) default '' null,
    key (user_id)
) comment '学生身份信息表';

create table `4_uper_up_intern`.user
(
    id          bigint auto_increment comment '用户ID'
        primary key,
    phone       varchar(20)                            not null comment '电话号码',
    openid      varchar(255)                           null comment '微信开放id',
    password    varchar(255)                           null comment '密码',
    nick_name   varchar(100)                           not null comment '昵称',
    icon        varchar(255) default ''                null comment '头像',
    create_time datetime     default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    gender      varchar(10)                            null comment '性别',
    name        varchar(20)                            null comment '真实姓名
',
    birth_date  varchar(10)                            null comment '生日',
    wechat      varchar(255)                           null comment '微信',
    is_deleted  tinyint(1)   default 0                 not null comment '逻辑删除标志，0-未删除，1-已删除'
)
    comment '用户表' charset = utf8mb4;

create table `4_uper_up_intern`.user_enterprise_info
(
    id bigint not null
        primary key
);

create table `4_uper_up_intern`.user_school_friend_info
(
    id      bigint       not null
        primary key,
    user_id bigint       not null,
    name    varchar(255) null
);

create table `4_uper_up_intern`.user_student_info
(
    id                bigint auto_increment comment 'ID'
        primary key,
    user_id           bigint                                 not null comment '用户ID, 外键',
    name              varchar(255)                           null comment '姓名',
    gender            varchar(10)                            null comment '性别，例如：男 / 女',
    phone             varchar(20)                            null comment '联系电话',
    birth_date        varchar(10)                            null comment '出生年月，格式为 yyyy/MM，例如：2003/10',
    school_name       varchar(255)                           null comment '学校名称',
    major             varchar(255)                           null comment '专业名称',
    icon              varchar(255) default ''                null comment '用户头像路径，默认空字符串',
    graduation_date   varchar(20)                            null comment '毕业时间，格式：YYYY-MM-DD',
    wechat            varchar(50)                            null comment '微信号',
    education_level   varchar(10)                            null comment '学历（本科、大专等）',
    advantages        text                                   null comment '个人优势',
    expected_position varchar(255)                           null comment '期望职位，例如：互联网-产品实习生',
    create_time       datetime     default CURRENT_TIMESTAMP null comment '创建时间',
    update_time       datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted        tinyint(1)   default 0                 not null comment '逻辑删除标志，0-未删除，1-已删除'
)
    comment '在线简历表' charset = utf8mb4;

create index idx_user_id
    on `4_uper_up_intern`.user_student_info (user_id);

create table `4_uper_up_intern`.user_teacher_info
(
    id      bigint not null
        primary key,
    user_id bigint not null
);

create table `4_uper_up_intern`.teacher_identification
(
    id              bigint auto_increment
        primary key,
    user_id         bigint                  not null comment '用户ID',
    name            varchar(255) default '' null comment '教师姓名',
    school_name     varchar(255) default '' null comment '学院名称',
    teacher_id_card varchar(255) default '' null comment '教职工证',
    is_deleted      tinyint(1)   default 0  not null comment '逻辑删除标志，0-未删除，1-已删除',
    key (user_id)
) comment '教师身份认证表';

create table `4_uper_up_intern`.enterprise_identification
(
    id                 bigint auto_increment
        primary key,
    user_id            bigint                  not null comment '用户ID',
    enterprise_name    varchar(255) default '' null comment '企业名称',
    enterprise_type    varchar(255) default '' null comment '企业类型',
    enterprise_scale   varchar(255) default '' null comment '企业规模',
    enterprise_id_card varchar(255) default '' null comment '营业执照',
    is_deleted         tinyint(1)   default 0  not null comment '逻辑删除标志，0-未删除，1-已删除',
    key (user_id)
) comment '企业身份认证表';

create table `4_uper_up_intern`.school_friend_identification
(
    id                  bigint auto_increment
        primary key,
    user_id             bigint                  not null comment '用户ID',
    graduation_certificate varchar(255) default '' null comment '毕业证书',
    is_deleted          tinyint(1)   default 0  not null comment '逻辑删除标志，0-未删除，1-已删除'
)
