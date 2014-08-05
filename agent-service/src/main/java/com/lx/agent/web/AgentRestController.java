package com.lx.agent.web;

import com.core.platform.web.rest.RESTController;
import com.core.platform.web.site.cookie.RequireCookie;
import com.core.platform.web.site.scheme.HTTPSOnly;
import com.core.platform.web.site.session.RequireSession;
import com.lx.agent.domain.User;

import javax.inject.Inject;

/**
 * @author hubery.chen
 */

@HTTPSOnly
@RequireCookie
@RequireSession
public class AgentRestController extends RESTController {

    private SiteContext siteContext;

    public void login(User user) {
        siteContext.login(user);
    }

    public User getUser() {
        return siteContext.getUser();
    }

    public String getUserName() {
        return siteContext.getUserName();
    }

    public void refresh(User user) {
        siteContext.refresh(user);
    }

    @Inject
    public void setSiteContext(SiteContext siteContext) {
        this.siteContext = siteContext;
    }
}
