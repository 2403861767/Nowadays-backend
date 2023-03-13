-- 用户表
create table user
(
    id           bigint auto_increment comment '主键'
        primary key,
    userAccount  varchar(255)                       null comment '账号',
    userName     varchar(255)                       null comment '用户名',
    userPassword varchar(255)                       null comment '密码',
    avatarUrl    varchar(255)                       null comment '用户头像地址',
    gender       tinyint                            null comment '性别 (0为男1位女)',
    phone        varchar(255)                       null comment '手机号',
    email        varchar(255)                       null comment '邮箱',
    userRole     tinyint  default 0                 null comment '用户的权限(0为普通用户，1为管理员)',
    isDelete     tinyint  default 0                 null comment '是否删除(0为否，1为是)',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
);
-- 标签表
create table tag
(
    id           bigint auto_increment comment '主键'
        primary key,
    tagName     varchar(20)                       null comment '用户名',
    isDelete     tinyint  default 0                 null comment '是否删除(0为否，1为是)',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
);
-- 计划表
create table plan
(
    id          bigint auto_increment comment '主键'
        primary key,
    tagId       bigint                             null comment '所属的标签',
    planTitle   varchar(100)                       not null comment '计划标题',
    description text                               null comment '描述',
    status      int      default 0                 not null comment '计划此时的状态，0表示未完成，1表示已经完成该计划',
    priority    int      default 0                 not null comment '优先级，0表示正常，1表示优先，2表示紧急',
    userId      bigint                             not null comment '创建人id',
    isDelete    tinyint  default 0                 null comment '是否删除(0为否，1为是)',
    createTime  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
);




-- id：主键 bigint notnull
-- tagId：所属的tagid bigint
-- title： 标题 varchar(100) NOT NULL,
-- description：描述 text
-- status：状态 0表示未完成，1表示完成 int(1) NOT NULL DEFAULT '0',
-- isDelete：是否删除 tinyint 0表示删除 1表示未删除 默认0
-- priority ：优先级，0表示正常，1表示优先，2表示紧急 int(2) NOT NULL DEFAULT '0',
-- createTime：创建时间 datetime 默认CURRENT_TIMESTAMP
-- updateTime：更新时间 datetime 默认 CURRENT_TIMESTAMP 根据当前时间戳更新
