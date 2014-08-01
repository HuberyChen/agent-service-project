package com.lx.agent.service;

import com.core.utils.StringUtils;
import com.lx.agent.domain.User;
import com.lx.agent.web.SiteContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author hubery.chen
 */
@Service
public class PermissionService {
    private SiteContext siteContext;

    public String getUserPermission(User user) {
        return user.getPermission().name();
    }

    public String getCurrentUserPermissionList() {
        User user = siteContext.getUser();
        return getUserPermission(user);
    }

    public boolean isCurrentVendorUserHasPermission(String permission) {
        User user = siteContext.getUser();
        if (StringUtils.equals(permission, user.getPermission().name())) {
            return true;
        }
        return false;
    }

    @Inject
    public void setSiteContext(SiteContext siteContext) {
        this.siteContext = siteContext;
    }

}
