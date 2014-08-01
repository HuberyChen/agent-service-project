package com.lx.agent.inteceptor;

import com.core.platform.web.ControllerHelper;
import com.core.platform.web.site.session.SecureSessionContext;
import com.core.utils.StringUtils;
import com.lx.agent.exception.PermissionForbiddenException;
import com.lx.agent.web.SecureSessionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class PermissionInterceptor extends HandlerInterceptorAdapter {

    private SecureSessionContext secureSessionContext;
    private final Logger logger = LoggerFactory.getLogger(HandlerInterceptorAdapter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            PermissionAllow permissionAllow = ControllerHelper.findMethodOrClassLevelAnnotation(handler, PermissionAllow.class);
            if (permissionAllow != null && !permissionAllow(permissionAllow.value())) {
                throw new PermissionForbiddenException("you are not allowed to access this page.");
            }
        }
        return true;
    }

    boolean permissionAllow(Permission[] permissions) {
        if (permissions.length == 0)
            return false;
        String user = secureSessionContext.get(SecureSessionConstants.USER_DETAILS);

        logger.debug("user permission:{}", user);
        logger.debug("controller action permission:{}", Arrays.toString(permissions));

        if (!StringUtils.hasText(user))
            return false;

        for (Permission permission : permissions) {
            if (StringUtils.equals(permission.getName(), user))
                return true;
        }

        return false;
    }

    @Inject
    public void setSecureSessionContext(SecureSessionContext secureSessionContext) {
        this.secureSessionContext = secureSessionContext;
    }
}
