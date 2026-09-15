package com.isuzuki.examples.oss;

/*
CREATE TABLE s3_file_store
(
    `id`               int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `provider`         varchar(20)  DEFAULT '' COMMENT 's3提供商',
    `file_id`          varchar(20)  DEFAULT '' COMMENT '文件id',
    `app_id`           varchar(20)  DEFAULT '' COMMENT '应用id',
    `biz_type`         varchar(20)  DEFAULT '' COMMENT '业务类型',
    `filename`         varchar(1024)  DEFAULT '' COMMENT '文件名字',
    `bucket`           varchar(128) DEFAULT '' COMMENT '存储Bucket',
    `object_key`       varchar(255) DEFAULT '' COMMENT '存储文件路径',
    `size`             int(11)      DEFAULT 0 COMMENT '图片大小 bytes',
    `width`            int(11)      DEFAULT 0 COMMENT '图片宽',
    `height`           int(11)      DEFAULT 0 COMMENT '图片高',
    `format`           varchar(55)  DEFAULT '' COMMENT '文件类型',
    `mime_type`        varchar(128) DEFAULT '' COMMENT '文件类型',
    `etag`             varchar(64)  DEFAULT '' COMMENT '第三方服务标签',
    `file_md5`         varchar(64)  DEFAULT '' COMMENT '原文件的hash值',
    `version_id`       varchar(64)  DEFAULT '' COMMENT '版本id',
    `acl`              varchar(32)  DEFAULT '' COMMENT 'ACL控制',
    `client_ip`        varchar(32)  DEFAULT '' COMMENT '客户端IP',
    `trace_id`         varchar(64)  DEFAULT '' COMMENT '服务中的链路ID',
    `s3_request_id`    varchar(64)  DEFAULT '' COMMENT 'S3提供商的链路ID',
    `create_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `update_by`        varchar(32)  DEFAULT '' COMMENT '修改人',
    `create_by`        varchar(32)  DEFAULT '' COMMENT '创建人',
    `record_status`    tinyint(2)   DEFAULT 1 COMMENT '1：有效记录，0：无效记录',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX              `idx_file_id`(`file_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT = '上传文件信息';
 */