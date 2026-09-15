package com.isuzuki.jdbc.datasources.autoconfigure;

import com.isuzuki.jdbc.datasources.DynamicDataSource;
import com.isuzuki.jdbc.datasources.DynamicDataSourceConstants;
import com.isuzuki.jdbc.datasources.DynamicDataSourceProperties;
import com.isuzuki.jdbc.datasources.annotation.DynamicLookup;
import com.isuzuki.jdbc.datasources.aop.DynamicDataSourceAnnotationAdvisor;
import com.isuzuki.jdbc.datasources.aop.DynamicDataSourceAnnotationInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 
 * @date : 2023/4/10
 * @description :
 */
@Configuration
@ConditionalOnProperty(prefix = DynamicDataSourceProperties.PREFIX, name = "enable", havingValue = "true")
public class DynamicDatasourceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor() {
        DynamicDataSourceAnnotationInterceptor interceptor = new DynamicDataSourceAnnotationInterceptor();

        Pointcut cpc = new AnnotationMatchingPointcut(DynamicLookup.class, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(DynamicLookup.class);
        ComposablePointcut pointcut = new ComposablePointcut(cpc).union(mpc);

        DynamicDataSourceAnnotationAdvisor advisor = new DynamicDataSourceAnnotationAdvisor(pointcut, interceptor);
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties(prefix = DynamicDataSourceProperties.PREFIX)
    public DynamicDataSourceProperties dynamicDataSourceProperties() {
        return new DynamicDataSourceProperties();
    }

    @Bean("dynamicDateSource")
    @ConditionalOnMissingBean(DynamicDataSource.class)
    public DataSource dynamicDateSource(DynamicDataSourceProperties dynamicDataSourceProperties) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        int size = dynamicDataSourceProperties.getSlave() == null ? 0 : dynamicDataSourceProperties.getSlave().size();
        Map<Object, Object> targetDataSources = new HashMap<>(size + 1, 1.0F);

        DataSourceProperties master = dynamicDataSourceProperties.getMaster();
        if (master == null) {
            throw new RuntimeException("DataSource [spring.dynamic.datasource.master] cannot be empty.");
        }
        DataSource masterDataSource = createDataSource(dynamicDataSourceProperties.getMaster());
        targetDataSources.put(DynamicDataSourceConstants.MASTER, masterDataSource);
        createMultipleDatesSource(targetDataSources, dynamicDataSourceProperties.getSlave());

        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }

    private void createMultipleDatesSource(Map<Object, Object> targetDataSources,
                                   Map<String, DataSourceProperties> dataSources) {
        if (dataSources == null) {
            return;
        }
        dataSources.forEach((key, dataSourceProperties) -> {
            if (DynamicDataSourceConstants.MASTER.equals(key)) {
                throw new RuntimeException("DataSource [spring.dynamic.datasource.slave.master] cannot appear.");
            }
            targetDataSources.put(key, createDataSource(dataSourceProperties));
        });
    }

    private DataSource createDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
}
