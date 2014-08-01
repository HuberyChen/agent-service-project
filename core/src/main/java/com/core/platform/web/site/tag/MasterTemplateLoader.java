package com.core.platform.web.site.tag;

import com.core.platform.web.site.view.DefaultFreemarkerView;
import com.core.platform.web.site.view.DefaultFreemarkerViewResolver;
import freemarker.template.Template;

import java.io.IOException;
import java.util.Locale;

public class MasterTemplateLoader {
    private final DefaultFreemarkerViewResolver viewResolver;
    private final DefaultFreemarkerView view;
    private final Locale locale;

    public MasterTemplateLoader(DefaultFreemarkerViewResolver viewResolver, DefaultFreemarkerView view, Locale locale) {
        this.viewResolver = viewResolver;
        this.view = view;
        this.locale = locale;
    }

    public Template loadTemplate(String template) throws IOException {
        String fullTemplatePath = viewResolver.buildFullTemplatePath(template);
        return view.loadTemplate(fullTemplatePath, locale);
    }
}
