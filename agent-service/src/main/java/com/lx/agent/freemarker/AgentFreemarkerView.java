package com.lx.agent.freemarker;

import com.core.platform.web.site.view.DefaultFreemarkerView;
import com.lx.agent.freemarker.tag.PermissionAllowTag;
import freemarker.template.TemplateModelException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @version createTime：Jun 3, 2013 2:42:56 PM
 */
public class AgentFreemarkerView extends DefaultFreemarkerView {

    private static final String PERMISSION_ALLOW_TAG_NAME = "permissionAllow";

    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        super.exposeHelpers(model, request);
        this.registerPermissionAllowTag(model);
    }

    private void registerPermissionAllowTag(Map<String, Object> model) throws TemplateModelException {
        Object previousValue = model.put(PERMISSION_ALLOW_TAG_NAME, new PermissionAllowTag());
        if (null != previousValue)
            throw new TemplateModelException(String.format("%1$s is reserved name in model as @%1$s, please use different name in model", PERMISSION_ALLOW_TAG_NAME));
    }
}
