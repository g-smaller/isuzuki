package com.isuzuki.oss.aliyun;

import com.isuzuki.oss.api.CloudOssProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = CloudOssProperties.PREFIX, name = CloudOssProperties.PROVIDER, havingValue = "aliyun")
@EnableConfigurationProperties({AliYunCloudOssProperties.class})
public class AliYunClientModuleAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties(prefix = CloudOssProperties.PREFIX)
    public CloudOssProperties cloudOssProperties() {
        return new CloudOssProperties();
    }

    @Bean
    public AliYunCloudOssClientTemplate aliYunCloudOssClientTemplate(CloudOssProperties cloudOssProperties, AliYunCloudOssProperties aliYunCloudOssProperties) {
        return new AliYunCloudOssClientTemplate(cloudOssProperties, aliYunCloudOssProperties);
    }
}
