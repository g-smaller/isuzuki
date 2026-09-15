package com.isuzuki.oss.s3;

import com.isuzuki.oss.api.CloudOssProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = CloudOssProperties.PREFIX, name = CloudOssProperties.PROVIDER, havingValue = "s3", matchIfMissing = true)
public class S3ClientModuleAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties(prefix = CloudOssProperties.PREFIX)
    public CloudOssProperties cloudOssProperties() {
        return new CloudOssProperties();
    }

    @Bean
    public S3CloudOssClientTemplate s3CloudOssClientTemplate(CloudOssProperties cloudOssProperties) {
        return new S3CloudOssClientTemplate(cloudOssProperties);
    }
}
