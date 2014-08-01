package com.core.platform.web.site.view;

import com.core.platform.web.DeploymentSettings;
import com.core.platform.web.site.SiteSettings;
import com.core.platform.web.site.cdn.CDNSettings;
import com.core.platform.web.site.tag.BodyTag;
import com.core.platform.web.site.tag.CDNTag;
import com.core.platform.web.site.tag.CSSTag;
import com.core.platform.web.site.tag.JSTag;
import com.core.platform.web.site.tag.MasterTag;
import com.core.platform.web.site.tag.MasterTemplateLoader;
import com.core.platform.web.site.tag.TagNames;
import com.core.platform.web.site.tag.URLTag;
import freemarker.template.Template;
import freemarker.template.TemplateModelException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class DefaultFreemarkerView extends FreeMarkerView {
    private static final String ATTRIBUTE_CONTEXT_INITIALIZED = DefaultFreemarkerView.class.getName() + ".CONTEXT_INITIALIZED";

    SiteSettings siteSettings;
    DeploymentSettings deploymentSettings;
    CDNSettings cdnSettings;
    DefaultFreemarkerViewResolver viewResolver;

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        ApplicationContext applicationContext = getApplicationContext();
        siteSettings = applicationContext.getBean(SiteSettings.class);
        cdnSettings = applicationContext.getBean(CDNSettings.class);
        deploymentSettings = applicationContext.getBean(DeploymentSettings.class);
        viewResolver = applicationContext.getBean(DefaultFreemarkerViewResolver.class);
    }

    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        // if exception occurs in rendering view, since the attribute will be exposed to model, so the tag can be registered before
        if (!initialized(request)) {
            registerURLTag(model, request);
            registerCDNTag(model, request);
            registerMasterTag(model, request);
            registerJSTag(model, request);
            registerCSSTag(model, request);

            request.setAttribute(ATTRIBUTE_CONTEXT_INITIALIZED, Boolean.TRUE);
        }
    }

    private boolean initialized(HttpServletRequest request) {
        Boolean initialized = (Boolean) request.getAttribute(ATTRIBUTE_CONTEXT_INITIALIZED);
        return Boolean.TRUE.equals(initialized);
    }

    void assertTagNameIsAvailable(Object previousValue, String tagName) throws TemplateModelException {
        if (previousValue != null)
            throw new TemplateModelException(String.format("%1$s is reserved name in model as @%1$s, please use different name in model", tagName));
    }

    private void registerURLTag(Map<String, Object> model, HttpServletRequest request) throws TemplateModelException {
        Object previousValue = model.put(TagNames.TAG_URL, new URLTag(request, deploymentSettings));
        assertTagNameIsAvailable(previousValue, TagNames.TAG_URL);
    }

    private void registerJSTag(Map<String, Object> model, HttpServletRequest request) throws TemplateModelException {
        Object previousValue = model.put(TagNames.TAG_JS, new JSTag(request, cdnSettings, siteSettings, deploymentSettings));
        assertTagNameIsAvailable(previousValue, TagNames.TAG_JS);
    }

    private void registerCSSTag(Map<String, Object> model, HttpServletRequest request) throws TemplateModelException {
        Object previousValue = model.put(TagNames.TAG_CSS, new CSSTag(request, cdnSettings, siteSettings, deploymentSettings));
        assertTagNameIsAvailable(previousValue, TagNames.TAG_CSS);
    }

    private void registerCDNTag(Map<String, Object> model, HttpServletRequest request) throws TemplateModelException {
        Object previousValue = model.put(TagNames.TAG_CDN, new CDNTag(request, cdnSettings, deploymentSettings));
        assertTagNameIsAvailable(previousValue, TagNames.TAG_CDN);
    }

    private void registerMasterTag(Map<String, Object> model, HttpServletRequest request) throws TemplateModelException {
        Locale locale = RequestContextUtils.getLocale(request);
        MasterTemplateLoader templateLoader = new MasterTemplateLoader(viewResolver, this, locale);
        Object previousValue = model.put(TagNames.TAG_MASTER, new MasterTag(model, templateLoader));
        assertTagNameIsAvailable(previousValue, TagNames.TAG_MASTER);

        previousValue = model.put(TagNames.TAG_BODY, new BodyTag(model));
        assertTagNameIsAvailable(previousValue, TagNames.TAG_BODY);
    }

    public Template loadTemplate(String fullTemplatePath, Locale locale) throws IOException {
        return getTemplate(fullTemplatePath, locale);
    }
}
