package com.core.platform;

import com.core.json.JSONBinder;
import com.core.platform.monitor.web.MonitorControllerConfig;
import com.core.platform.web.DeploymentSettings;
import com.core.platform.web.request.RequestContextImpl;
import com.core.platform.web.request.RequestContextInterceptor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.annotation.ErrorsMethodArgumentResolver;
import org.springframework.web.method.annotation.ExpressionValueMethodArgumentResolver;
import org.springframework.web.method.annotation.MapMethodProcessor;
import org.springframework.web.method.annotation.RequestHeaderMapMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestHeaderMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.PathVariableMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestPartMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@EnableWebMvc
@Import({MonitorControllerConfig.class})
public class AbstractWebConfig extends WebMvcConfigurerAdapter {
    @Bean
    public DeploymentSettings deploymentSettings() {
        return DeploymentSettings.get();
    }

    @Bean
    public List<HttpMessageConverter<?>> messageConverters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new ByteArrayHttpMessageConverter());
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setWriteAcceptCharset(false);
        ArrayList<MediaType> textTypes = new ArrayList<>();
        textTypes.add(MediaType.TEXT_PLAIN);
        textTypes.add(MediaType.TEXT_HTML);
        textTypes.add(MediaType.TEXT_XML);
        textTypes.add(MediaType.APPLICATION_XML);
        textTypes.add(MediaType.APPLICATION_JSON);
        stringConverter.setSupportedMediaTypes(textTypes);
        converters.add(stringConverter);
        converters.add(new Jaxb2RootElementHttpMessageConverter());
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(JSONBinder.getObjectMapper());
        converters.add(jsonConverter);
        return converters;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.addAll(messageConverters());
    }

    @Inject
    ConfigurableBeanFactory beanFactory;

    /**
     * @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // Annotation-based argument resolution
        resolvers.add(new RequestParamMethodArgumentResolver(beanFactory, false));
        resolvers.add(new RequestParamMapMethodArgumentResolver());
        resolvers.add(new PathVariableMethodArgumentResolver());
        resolvers.add(new ServletModelAttributeMethodProcessor(false));
        resolvers.add(new RequestResponseBodyMethodProcessor(messageConverters()));
        resolvers.add(new RequestPartMethodArgumentResolver(messageConverters()));
        resolvers.add(new RequestHeaderMethodArgumentResolver(beanFactory));
        resolvers.add(new RequestHeaderMapMethodArgumentResolver());
        resolvers.add(new ExpressionValueMethodArgumentResolver(beanFactory));

        // Type-based argument resolution
        resolvers.add(new MapMethodProcessor());
        resolvers.add(new ErrorsMethodArgumentResolver());
    }

    @Bean
    public RequestContextInterceptor requestContextInterceptor() {
        return new RequestContextInterceptor();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    RequestContextImpl requestContext() {
        return new RequestContextImpl();
    }

}
