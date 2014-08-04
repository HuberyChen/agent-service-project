package com.lx.agent;

import com.core.platform.DefaultSiteWebConfig;
import com.core.platform.web.DeploymentSettings;
import com.core.platform.web.site.SiteSettings;
import com.core.platform.web.site.layout.ModelBuilderInterceptor;
import com.core.platform.web.site.view.DefaultFreemarkerViewResolver;
import com.lx.agent.freemarker.AgentFreemarkerView;
import com.lx.agent.inteceptor.LoginRequiredInterceptor;
import com.lx.agent.inteceptor.MasterLayout;
import com.lx.agent.inteceptor.MasterTemplateModelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;

@Configuration
public class WebConfig extends DefaultSiteWebConfig {
    @Inject
    Environment env;

    @Inject
    ServletContext servletContext;

    @Override
    public DeploymentSettings deploymentSettings() {
        DeploymentSettings settings = super.deploymentSettings();
        settings.setHTTPPort(env.getRequiredProperty("site.httpPort", int.class));
        settings.setHTTPSPort(env.getRequiredProperty("site.httpsPort", int.class));
        settings.setDeploymentContext(env.getProperty("site.deploymentContext"), servletContext);
        return settings;
    }

    @Override
    public SiteSettings siteSettings() {
        SiteSettings settings = new SiteSettings();
        settings.setErrorPage("/error");
        settings.setResourceNotFoundPage("forward:/error/resource-not-found");
        settings.setSessionTimeOutPage("redirect:/home");
        settings.setJSDir("/dstatic/js");
        settings.setCSSDir("/dstatic/css");
        return settings;
    }

    @Bean
    DefaultFreemarkerViewResolver freeMarkerViewResolver() {
        DefaultFreemarkerViewResolver resolver = new DefaultFreemarkerViewResolver();
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setViewClass(AgentFreemarkerView.class);
        resolver.setExposeSpringMacroHelpers(false);
        resolver.setExposeRequestAttributes(true);
        resolver.setAllowRequestOverride(true);
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // on server env, /mstatic will be handled by apache or CDN, this only
        // apply to local dev
        registry.addResourceHandler("/dstatic/**").addResourceLocations("/dstatic/");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Bean
    public ModelBuilderInterceptor modelBuilderInterceptor() {
        ModelBuilderInterceptor interceptor = new ModelBuilderInterceptor();
        interceptor.registerModelBuilder(MasterLayout.class, MasterTemplateModelBuilder.class);
        return interceptor;
    }

    @Bean
    public LoginRequiredInterceptor loginRequiredInterceptor() {
        return new LoginRequiredInterceptor();
    }

    @Inject
    EntityManagerFactory entityManagerFactory;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(exceptionInterceptor());
        registry.addInterceptor(requestContextInterceptor());
        registry.addInterceptor(httpSchemeEnforceInterceptor());
        registry.addInterceptor(cookieInterceptor());
        registry.addInterceptor(sessionInterceptor());
        registry.addInterceptor(loginRequiredInterceptor());
        registry.addInterceptor(modelBuilderInterceptor());

        OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor = new OpenEntityManagerInViewInterceptor();
        openEntityManagerInViewInterceptor.setEntityManagerFactory(entityManagerFactory);
        registry.addWebRequestInterceptor(openEntityManagerInViewInterceptor);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/login");
    }
}
