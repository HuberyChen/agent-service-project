package com.lx.agent.web;

import com.core.json.JSONBinder;
import com.core.platform.exception.SessionTimeOutException;
import com.core.platform.web.request.RequestContext;
import com.core.platform.web.site.session.SecureSessionContext;
import com.core.platform.web.site.session.SessionContext;
import com.lx.agent.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @version createTime：May 8, 2013 1:31:50 PM
 */

@Component
public class SiteContext {

    private SessionContext sessionContext;
    // secureSession must use under https
    private SecureSessionContext secureSessionContext;
    private RequestContext requestContext;

    public String getRelativeUrl() {
        return requestContext.getClientRequestedRelativeURL();
    }

    public Map<String, Object> getModel() {
        Map<String, Object> model = new HashMap<>();
        model.put("user", getUserName());

        return model;
    }

    public void login(User user) {
        sessionContext.set(SecureSessionConstants.LOGGED_IN, Boolean.TRUE);
        secureSessionContext.set(SecureSessionConstants.LOGGED_IN, Boolean.TRUE);
        String json = JSONBinder.binder(User.class).toJSON(user);
        sessionContext.set(SecureSessionConstants.USER_DETAILS, json);
        secureSessionContext.set(SecureSessionConstants.USER_DETAILS, json);
    }

    public void signOut() {
        sessionContext.invalidate();
        secureSessionContext.invalidate();
    }

    public User getUser() {
        String json = requestContext.isSecure() ? secureSessionContext.get(SecureSessionConstants.USER_DETAILS) : sessionContext.get(SecureSessionConstants.USER_DETAILS);
        // maybe not need
        if (StringUtils.isBlank(json))
            throw new SessionTimeOutException("session timeout.");
        return JSONBinder.binder(User.class).fromJSON(json);
    }

    public String getUserName() {
        return getUser().getFirstName() + " " + getUser().getLastName();
    }

    public boolean loggedIn() {
        Boolean loggedIn = secureSessionContext.get(SecureSessionConstants.LOGGED_IN);
        return Boolean.TRUE.equals(loggedIn);
    }

    public void refresh(User user) {
        String json = JSONBinder.binder(User.class).toJSON(user);
        sessionContext.set(SecureSessionConstants.USER_DETAILS, json);
        secureSessionContext.set(SecureSessionConstants.USER_DETAILS, json);
    }

    @Inject
    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    @Inject
    public void setSecureSessionContext(SecureSessionContext secureSessionContext) {
        this.secureSessionContext = secureSessionContext;
    }

    @Inject
    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

}
