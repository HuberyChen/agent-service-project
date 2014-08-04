package com.lx.agent.inteceptor;


import com.core.platform.web.site.layout.ModelBuilder;
import com.lx.agent.freemarker.tag.PermissionAllowTag;
import com.lx.agent.web.SiteContext;

import javax.inject.Inject;
import java.util.Map;

/**
 * @author neo
 */
public class MasterTemplateModelBuilder implements ModelBuilder {
    @Inject
    private SiteContext siteContext;

    @Override
    public void build(Map<String, Object> model) {
        model.put("user", siteContext.getUser().getEmailAddress());
        model.put(PermissionAllowTag.PERMISSIONVALUEKEY, siteContext.getUser().getPermission().toString());
    }
}
