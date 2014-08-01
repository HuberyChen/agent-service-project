package com.core.platform.web;

import com.core.utils.ReadOnly;
import com.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;

public class DeploymentSettings {
    private final Logger logger = LoggerFactory.getLogger(DeploymentSettings.class);

    private static final DeploymentSettings INSTANCE = new DeploymentSettings();

    public static DeploymentSettings get() {
        return INSTANCE;
    }

    private final ReadOnly<String> deploymentContext = new ReadOnly<>();
    private final ReadOnly<Integer> httpPort = new ReadOnly<>();
    private final ReadOnly<Integer> httpsPort = new ReadOnly<>();

    public String getDeploymentContext() {
        if (!deploymentContext.assigned()) return "";
        return deploymentContext.value();
    }

    public void setDeploymentContext(String deploymentContext, ServletContext servletContext) {
        if (StringUtils.hasText(deploymentContext)) {
            this.deploymentContext.set(deploymentContext);
        } else {
            this.deploymentContext.set(servletContext.getContextPath());
        }
        logger.info("set deploymentContext={}", this.deploymentContext.value());
    }

    public Integer getHTTPPort() {
        return httpPort.value();
    }

    public void setHTTPPort(Integer httpPort) {
        this.httpPort.set(httpPort);
    }

    public Integer getHTTPSPort() {
        return httpsPort.value();
    }

    public void setHTTPSPort(Integer httpsPort) {
        this.httpsPort.set(httpsPort);
    }
}
