package com.hubery.agent;

import com.quidsi.core.crypto.EncryptionUtils;
import com.quidsi.core.database.ConnectionPoolDataSource;
import com.quidsi.core.platform.DefaultServiceWebConfig;
import com.quidsi.core.platform.web.DeploymentSettings;
import com.quidsi.core.platform.web.rest.security.ClientAuthorizationInterceptor;
import com.quidsi.core.platform.web.rest.security.DBClientServiceFactory;
import com.quidsi.core.util.ClasspathResource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

/**
 * @author neo
 */
@Configuration
public class WebConfig extends DefaultServiceWebConfig {
    @Inject
    Environment env;

    @Inject
    ServletContext servletContext;

    @Override
    public DeploymentSettings deploymentSettings() {
        DeploymentSettings settings = super.deploymentSettings();
        settings.setHTTPPort(env.getRequiredProperty("site.httpPort", int.class));
        settings.setHTTPSPort(env.getRequiredProperty("site.httpsPort", int.class));
        settings.setDeploymentContext(env.getProperty("site.deploymentContext"), servletContext);
        return settings;
    }

    @Bean
    public DataSource authDataSource() {
        ConnectionPoolDataSource dataSource = new ConnectionPoolDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.auth.driverClassName"));
        dataSource.setUrl(env.getRequiredProperty("jdbc.auth.url"));
        dataSource.setUsername(env.getRequiredProperty("jdbc.auth.username"));
        dataSource.setPassword(EncryptionUtils.decrypt(env.getRequiredProperty("jdbc.auth.password"), new ClasspathResource("private.key")));
        dataSource.setValidationQuery("select 1");
        dataSource.setDatabaseName("AuthDB");
        return dataSource;
    }

    @Bean
    public ClientAuthorizationInterceptor clientAuthorizationInterceptor() {
        ClientAuthorizationInterceptor interceptor = new ClientAuthorizationInterceptor();
        DBClientServiceFactory clientServiceFactory = new DBClientServiceFactory();
        clientServiceFactory.setAuthDataSource(authDataSource());
        interceptor.setClientServiceFactory(clientServiceFactory);
        return interceptor;
    }

    @Inject
    EntityManagerFactory entityManagerFactory;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(exceptionInterceptor());
        registry.addInterceptor(requestContextInterceptor());
        registry.addInterceptor(trackInterceptor());
        OpenEntityManagerInViewInterceptor interceptor = new OpenEntityManagerInViewInterceptor();
        interceptor.setEntityManagerFactory(entityManagerFactory);
        registry.addWebRequestInterceptor(interceptor);
        registry.addInterceptor(clientAuthorizationInterceptor());
    }
}
