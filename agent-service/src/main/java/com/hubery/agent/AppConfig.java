package com.hubery.agent;



import com.quidsi.core.crypto.EncryptionUtils;
import com.quidsi.core.database.ConnectionPoolDataSource;
import com.quidsi.core.database.JPAAccess;
import com.quidsi.core.log.LogSettings;
import com.quidsi.core.platform.DefaultAppConfig;
import com.quidsi.core.platform.PlatformScopeResolver;
import com.quidsi.core.platform.concurrent.TaskExecutor;
import com.quidsi.core.util.ClasspathResource;
import com.hubery.agent.service.agent-service-projectServiceLogMessageFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;


@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackageClasses = AppConfig.class, scopeResolver = PlatformScopeResolver.class)
public class AppConfig extends DefaultAppConfig {
    @Inject
    Environment env;

    @Bean
    public TaskExecutor taskExecutor() {
        return TaskExecutor.fixedSizeExecutor(25);
    }

    @Override
    public LogSettings logSettings() {
        LogSettings settings = LogSettings.get();
        settings.setLogMessageFilter(new agent-service-projectServiceLogMessageFilter());
        return settings;
    }

    @Bean
    public DataSource dataSource() {
        ConnectionPoolDataSource dataSource = new ConnectionPoolDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(EncryptionUtils.decrypt(env.getRequiredProperty("jdbc.password"), new ClasspathResource("private.key")));
        dataSource.setValidationQuery("select 1");
        dataSource.setDatabaseName(env.getRequiredProperty("jdbc.databaseName"));
        dataSource.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(AppConfig.class.getPackage().getName());
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform(env.getRequiredProperty("jdbc.sqlDialect"));
        vendorAdapter.setShowSql(false);
        vendorAdapter.setGenerateDdl(false);
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean
    public JPAAccess jpaAccess() {
        return new JPAAccess();
    }
}