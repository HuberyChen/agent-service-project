package com.lx.agent.inteceptor;

import com.core.http.HTTPConstants;
import com.core.platform.web.ControllerHelper;
import com.core.platform.web.DeploymentSettings;
import com.core.platform.web.site.session.SessionContext;
import com.core.platform.web.url.URLBuilder;
import com.lx.agent.web.SecureSessionConstants;
import org.apache.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginRequiredInterceptor extends HandlerInterceptorAdapter {

    private SessionContext sessionContext;

    private DeploymentSettings deploymentSettings;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            LoginRequired loginRequired = ControllerHelper.findMethodOrClassLevelAnnotation(handler, LoginRequired.class);
            if (null != loginRequired && !loggedIn() && isAjaxRequest(request)) {
                response.setStatus(HttpStatus.SC_UNAUTHORIZED);
                response.getWriter().write("The session is time out,please login again!");
                response.flushBuffer();
                return false;
            }

            if (null != loginRequired && !loggedIn() && !isAjaxRequest(request)) {
                redirectToSpecifiedPage(response, "/login");
                return false;
            }

        }
        return true;
    }

    boolean loggedIn() {
        Boolean loggedIn = sessionContext.get(SecureSessionConstants.LOGGED_IN);
        return Boolean.TRUE.equals(loggedIn);
    }

    private boolean redirectToSpecifiedPage(HttpServletResponse response, String specifiedURL) {
        URLBuilder builder = new URLBuilder();
        builder.setContextPath(deploymentSettings.getDeploymentContext());
        builder.setLogicalURL(specifiedURL);
        response.setStatus(HTTPConstants.SC_MOVED_TEMPORARILY);
        response.setHeader(HTTPConstants.HEADER_REDIRECT_LOCATION, builder.buildRelativeURL());
        return true;
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    @Inject
    public void setDeploymentSettings(DeploymentSettings deploymentSettings) {
        this.deploymentSettings = deploymentSettings;
    }

    @Inject
    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

}