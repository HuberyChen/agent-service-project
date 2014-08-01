package com.core.platform;

import com.core.log.ActionLoggerImpl;
import com.core.log.LogSettings;
import com.core.platform.context.PropertyContext;
import com.core.platform.exception.ErrorHandler;
import com.core.platform.monitor.MonitorManager;
import com.core.platform.monitor.ServerStatus;
import com.core.platform.monitor.exception.ExceptionMonitor;
import com.core.platform.monitor.exception.RecentExceptions;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Properties;

/**
 * @author hubery
 */
public class DefaultAppConfig {
    @Inject
    private ConfigurableEnvironment environment;

    @Bean
    static PropertyContext propertyContext() throws IOException {
        PropertyContext propertySource = new PropertyContext();
        propertySource.setLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:*.properties"));
        propertySource.loadAllProperties();
        return propertySource;
    }

    @PostConstruct
    void configureEnvironment() throws IOException {
        environment.setIgnoreUnresolvableNestedPlaceholders(true);
        Properties properties = propertyContext().getAllProperties();
        environment.getPropertySources().addLast(new PropertiesPropertySource("properties", properties));
    }

    @Bean
    ActionLoggerImpl actionLogger() {
        return ActionLoggerImpl.get();
    }

    @Bean
    ErrorHandler errorHandler() {
        return new ErrorHandler();
    }

    @Bean
    public ExceptionMonitor exceptionMonitor() {
        return new ExceptionMonitor();
    }

    @Bean
    public RecentExceptions recentExceptions() {
        return new RecentExceptions();
    }

    @Bean
    MonitorManager monitorManager() {
        return new MonitorManager();
    }

    @Bean
    public LogSettings logSettings() {
        return LogSettings.get();
    }

    @Bean
    ServerStatus serverStatus() {
        return new ServerStatus();
    }
}

