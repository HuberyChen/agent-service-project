package com.core.database;

import com.core.platform.monitor.ServiceMonitor;
import com.core.platform.monitor.ServiceStatus;
import com.core.utils.StringUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class ConnectionPoolDataSource extends BasicDataSource implements InitializingBean, DisposableBean, ServiceMonitor {
    private String databaseName;
    private final JDBCAccess jdbcAccess = new JDBCAccess();

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!StringUtils.hasText(databaseName))
            throw new IllegalArgumentException("databaseName is required");
        if (!StringUtils.hasText(getValidationQuery()))
            throw new IllegalArgumentException("validation query is required for connection pool");

        jdbcAccess.setDataSource(this);
    }

    @Override
    public void destroy() throws Exception {
        close();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("not supported");
    }

    @Override
    public ServiceStatus getServiceStatus() throws Exception {
        jdbcAccess.findString(getValidationQuery());
        return ServiceStatus.UP;
    }

    @Override
    public String getServiceName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
