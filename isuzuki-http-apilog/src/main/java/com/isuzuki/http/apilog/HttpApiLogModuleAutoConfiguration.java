package com.isuzuki.http.apilog;

import com.isuzuki.core.TraceContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = HttpApiLogProperties.PREFIX, name = "enabled", havingValue = "true")
@EnableConfigurationProperties(HttpApiLogProperties.class)
public class HttpApiLogModuleAutoConfiguration {

    @Bean
    public DefaultHttpApiLogCustomizer defaultHttpApiLogCustomizer() {
        return new  DefaultHttpApiLogCustomizer();
    }

    @Bean
    public MetadataHttpApiLogCustomizer metadataHttpApiLogCustomizer() {
        return new MetadataHttpApiLogCustomizer();
    }

    @Bean
    public ExtraHttpApiLogCustomizer extraHttpApiLogCustomizer() {
        return new ExtraHttpApiLogCustomizer();
    }

    @Bean
    public HttpRequestHttpApiLogCustomizer httpRequestHttpApiLogCustomizer() {
        return new HttpRequestHttpApiLogCustomizer();
    }

    @Bean
    public HttpResponseHttpApiLogCustomizer responseHttpApiLogCustomizer() {
        return new HttpResponseHttpApiLogCustomizer();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpServletRequestWrapperFactory httpServletRequestWrapperFactory() {
        return new DefaultHttpServletRequestWrapperFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpServletResponseWrapperFactory httpServletResponseWrapperFactory() {
        return new DefaultHttpServletResponseWrapperFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpApiLogBuilderFactory httpApiLogBuilderFactory(TraceContext traceContext) {
        return new DefaultHttpApiLogBuilderFactory(traceContext);
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpApiLogHandler  httpApiLogHandler() {
        return new DefaultHttpApiLogHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpApiLogFilter httpApiLogFilter(HttpApiLogBuilderFactory builderFactory,
                                             HttpApiLogHandler httpApiLogHandler,
                                             HttpServletRequestWrapperFactory requestWrapperFactory,
                                             HttpServletResponseWrapperFactory responseWrapperFactory,
                                             List<HttpApiLogCustomizer> customizers) {
        return new HttpApiLogFilter(builderFactory, httpApiLogHandler, requestWrapperFactory, responseWrapperFactory, customizers);
    }

}
