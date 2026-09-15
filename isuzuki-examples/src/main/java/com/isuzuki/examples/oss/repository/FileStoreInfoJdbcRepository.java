package com.isuzuki.examples.oss.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class FileStoreInfoJdbcRepository implements  FileStoreInfoRepository {

    private final JdbcTemplate jdbcTemplate;

    public FileStoreInfoJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(FileStoreInfo entity) {
        if (entity == null) {
            return;
        }

        String sql = "insert into s3_file_store (provider, file_id, app_id, biz_type, filename, bucket, object_key, size, width, height, format, mime_type, etag, file_md5, version_id, acl, client_ip, trace_id, s3_request_id, update_by, create_by) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?， ？)";

        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, entity.getProvider());
                ps.setString(2, entity.getFileId());
                ps.setString(3, entity.getAppId());
                ps.setString(4, entity.getBizType());
                ps.setString(5, entity.getFilename());
                ps.setString(6, entity.getBucket());
                ps.setString(7, entity.getObjectKey());
                ps.setObject(8, entity.getSize());
                ps.setObject(9, entity.getWidth());
                ps.setObject(10, entity.getHeight());
                ps.setString(11, entity.getFormat());
                ps.setString(12, entity.getMimeType());
                ps.setString(13, entity.getEtag());
                ps.setString(14, entity.getFileMd5());
                ps.setString(15, entity.getVersionId());
                ps.setString(16, entity.getAcl());
                ps.setString(17, entity.getClientIp());
                ps.setString(18, entity.getTraceId());
                ps.setString(19, entity.getS3RequestId());
                ps.setString(20, entity.getUpdateBy());
                ps.setString(21, entity.getCreateBy());
            }
        });
    }
}
