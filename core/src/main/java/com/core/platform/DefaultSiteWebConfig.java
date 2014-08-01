package com.core.platform;

import com.core.platform.web.site.cdn.CDNSettings;
import com.core.platform.web.site.cdn.DefaultCDNSettings;
import com.core.platform.web.site.session.SessionContext;
import com.core.platform.web.site.view.DefaultFreemarkerView;
import com.core.platform.web.site.view.DefaultFreemarkerViewResolver;
import com.core.utils.CharacterEncodings;
import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.inject.Singleton;
import java.util.Properties;

/**
 * @author neo
 */
@Singleton
public class DefaultSiteWebConfig extends AbstractWebConfig {
    @Bean
    public CDNSettings cdnSettings() {
        return new DefaultCDNSettings();
    }

    @Bean
    FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer config = new FreeMarkerConfigurer();
        config.setTemplateLoaderPath("/");
        Properties settings = new Properties();
        settings.setProperty(Configuration.DEFAULT_ENCODING_KEY, CharacterEncodings.UTF_8);
        settings.setProperty(Configuration.URL_ESCAPING_CHARSET_KEY, CharacterEncodings.UTF_8);
        settings.setProperty(Configuration.NUMBER_FORMAT_KEY, "0.##");
        settings.setProperty(Configuration.TEMPLATE_EXCEPTION_HANDLER_KEY, "rethrow");
        config.setFreemarkerSettings(settings);
        return config;
    }

    @Bean
    DefaultFreemarkerViewResolver freeMarkerViewResolver() {
        DefaultFreemarkerViewResolver resolver = new DefaultFreemarkerViewResolver();
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setViewClass(DefaultFreemarkerView.class);
        resolver.setExposeSpringMacroHelpers(false);
        resolver.setExposeRequestAttributes(true);
        resolver.setAllowRequestOverride(true);
        return resolver;
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    SessionContext sessionContext() {
        return new SessionContext();
    }

}
