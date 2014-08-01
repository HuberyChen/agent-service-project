package com.core.platform.web.site.tag;

import com.core.platform.web.DeploymentSettings;
import com.core.platform.web.site.SiteSettings;
import com.core.platform.web.site.cdn.CDNSettings;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class CSSTag extends CDNTagSupport implements TemplateDirectiveModel {
    public static final String TEMPLATE_CSS_TAG = "<link type=\"text/css\" rel=\"stylesheet\" href=\"%s\"%s/>";

    private final SiteSettings siteSettings;

    public CSSTag(HttpServletRequest request, CDNSettings cdnSettings, SiteSettings siteSettings, DeploymentSettings deploymentSettings) {
        super(request, cdnSettings, deploymentSettings);
        this.siteSettings = siteSettings;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        assertNoBody(body);
        String tags = buildCSSTags(params);
        Writer output = env.getOut();
        output.write(tags);
    }

    String buildCSSTags(Map<String, Object> params) throws IOException, TemplateModelException {
        return buildMultipleResourceTags("href", siteSettings.getCSSDir(), TEMPLATE_CSS_TAG, params);
    }
}
