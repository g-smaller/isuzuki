package com.isuzuki.oss.support;


import com.isuzuki.oss.api.CloudOssClientTemplate;
import com.isuzuki.oss.api.CloudOssProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnProperty(prefix = CloudOssProperties.PREFIX, name = CloudOssProperties.ENABLED, havingValue = "true")
public class CloudOssClientModuleAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CloudOssClient defaultCloudOssClient(CloudOssClientTemplate cloudOssClientTemplate) {
        return new DefaultCloudOssClient(cloudOssClientTemplate);
    }
}
