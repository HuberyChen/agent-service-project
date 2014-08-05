package com.lx.agent.web;

import com.core.platform.web.site.SiteController;
import com.core.platform.web.site.cookie.RequireCookie;
import com.core.platform.web.site.session.RequireSession;
import com.lx.agent.domain.User;
import com.lx.agent.exception.PermissionForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.Map;

/**
 * @author hubery.chen
 */
//@HTTPSOnly
@RequireCookie
@RequireSession
public class AgentSiteController extends SiteController {

    private SiteContext siteContext;

    private final Logger logger = LoggerFactory.getLogger(AgentSiteController.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView error(Throwable exception) {
        Map<String, Object> model = errorPageModelBuilder.buildErrorPageModel(exception);
        model.putAll(siteContext.getModel());
        return new ModelAndView(siteSettings.getErrorPage(), model);
    }

    @ExceptionHandler(PermissionForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView forbidden(PermissionForbiddenException exception) {
        logger.debug("permission forbidden " + exception.getMessage());
        return new ModelAndView(HttpStatus.FORBIDDEN.toString(), siteContext.getModel());

    }

    public void signOut() {
        siteContext.signOut();
    }

    public User getUser() {
        return siteContext.getUser();
    }

    public String getUserName() {
        return siteContext.getUserName();
    }

    public boolean loggedIn() {
        return siteContext.loggedIn();
    }

    @Inject
    public void setSiteContext(SiteContext siteContext) {
        this.siteContext = siteContext;
    }
}
