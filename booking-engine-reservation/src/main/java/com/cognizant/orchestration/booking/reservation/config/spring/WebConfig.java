package com.cognizant.orchestration.booking.reservation.config.spring;


import java.util.List;
import java.util.Properties;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.UrlFilenameViewController;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.cognizant.orchestration.booking.reservation.web.handler.ExtendedExceptionHandlerExceptionResolver;
import com.cognizant.orchestration.booking.reservation.web.handler.GlobalExceptionHandler;

@Configuration
@ComponentScan(basePackages = { "com.cognizant.orchestration" }, includeFilters = @Filter(Controller.class))
@EnableAspectJAutoProxy
public class WebConfig extends WebMvcConfigurationSupport {

  

    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> messageConverters) {
        MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJacksonHttpMessageConverter.setPrettyPrint(true);
        messageConverters.add(mappingJacksonHttpMessageConverter);
        super.configureMessageConverters(messageConverters);
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        globalExceptionHandler.setMessageSource(messageSource());
        return globalExceptionHandler;
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        ExtendedExceptionHandlerExceptionResolver customResolver = new ExtendedExceptionHandlerExceptionResolver();
        customResolver.setExceptionHandler(globalExceptionHandler());
        customResolver.setMessageConverters(getMessageConverters());
        customResolver.afterPropertiesSet();

        exceptionResolvers.add(customResolver);
        exceptionResolvers.add(new ResponseStatusExceptionResolver());
        exceptionResolvers.add(new DefaultHandlerExceptionResolver());
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setCacheSeconds(10);
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

  
    // Added for Spring view resolver
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/");
        resolver.setSuffix(".jsp");
        return resolver;

    }

    // This resolves URL from file names
    @Bean(name = "urlViewController")
    public UrlFilenameViewController getUrlViewController() {
        return new UrlFilenameViewController();
    }

    // This view resolver resolves HTML views to JSP
    @Bean
    public SimpleUrlHandlerMapping getUrlHandlerMapping() {
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        Properties mappings = new Properties();
        mappings.put("/**/*.html", "urlViewController");
        handlerMapping.setMappings(mappings);
        return handlerMapping;
    }

    // Added for Swagger UI
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       // registry.addResourceHandler("/docs/**").addResourceLocations(docsLocation);
    }
   
}
