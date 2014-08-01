package com.core.platform;

import com.core.web.ExceptionInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @author neo
 */
public class DefaultServiceWebConfig extends AbstractWebConfig {

    @Bean
    public ExceptionInterceptor exceptionInterceptor() {
        return new ExceptionInterceptor();
    }
}
