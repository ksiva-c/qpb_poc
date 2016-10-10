package com.demo.limits.web.config;


import com.demo.limits.common.config.CommonConfig;
import com.demo.limits.core.config.CoreConfig;
import com.demo.limits.idm.config.IDMConfig;
import com.demo.limits.repository.config.HikariCPDataSourceConfig;
import com.demo.limits.repository.config.RepositoryConfig;
import com.demo.limits.servicessupport.config.ServicesSupportConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Set;

public class LimitsRestServerInitializer implements WebApplicationInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(LimitsRestServerInitializer.class);

    @Resource
    private Environment env;

    @Override
    public void onStartup(ServletContext container) {
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.setDisplayName("limits 360 API");
        // Register application config
        rootContext.register(
                AppConfig.class,
                CacheConfig.class,
                CoreConfig.class,
                IDMConfig.class,
                SwaggerConfig.class,
                HikariCPDataSourceConfig.class,
                RepositoryConfig.class,
                CommonConfig.class,
                PropertiesConfig.class,
                ServicesSupportConfig.class
        );

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));

        // Register Encoding Filter
        addEncodingFilter(container);

        // Register Logging Filter
        addLoggingFilter(container);

        //Register Api Filter
        addAPIContextFilter(container);

        // Swagger CORS filter
        // addSwaggerCORSFilter(container);

        // Register and map the dispatcher servlet
        addServiceDispatcherServlet(container, rootContext);
    }


    private void addServiceDispatcherServlet(ServletContext container, AnnotationConfigWebApplicationContext rootContext) {
        final String SERVICES_MAPPING = "/";

        ServletRegistration.Dynamic dispatcher = container.addServlet("servicesDispatcher", new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.setMultipartConfig(new MultipartConfigElement("", 1024*1024*50, 1024*1024*50, 1));
        dispatcher.setAsyncSupported(true);

        Set<String> mappingConflicts = dispatcher.addMapping(SERVICES_MAPPING);

        if (!mappingConflicts.isEmpty()) {
            for (String s : mappingConflicts) {
                LOG.error("Mapping conflict: " + s);
            }
            throw new IllegalStateException("'ServicesDispatcher' could not be mapped to '" + SERVICES_MAPPING + "'");
        }
    }

    private void addEncodingFilter(ServletContext container) {
        FilterRegistration.Dynamic fr = container.addFilter("encodingFilter", new CharacterEncodingFilter());
        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, false, "/*");
    }

    private void addLoggingFilter(ServletContext container) {
        FilterRegistration.Dynamic fr = container.addFilter("loggingFilter", new CommonsRequestLoggingFilter());
        fr.addMappingForUrlPatterns(null, false, "/*");
    }

    private void addAPIContextFilter(ServletContext container) {
        FilterRegistration.Dynamic fr = container.addFilter("apiFilter", new DelegatingFilterProxy("apiContextFilter"));
        fr.addMappingForUrlPatterns(null, false, "/*");
    }

    private void addSwaggerCORSFilter(ServletContext container) {
        FilterRegistration.Dynamic fr = container.addFilter("swaggerCorsFilter",
                new DelegatingFilterProxy("swaggerCORSFilter"));
        fr.addMappingForUrlPatterns(null, false, "/*");
    }
}

