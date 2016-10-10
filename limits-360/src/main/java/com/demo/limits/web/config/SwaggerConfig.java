package com.demo.limits.web.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.controllers.DefaultSwaggerController;
import com.mangofactory.swagger.paths.SwaggerPathProvider;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

/**
 * Created by lnv.
 */
@Configuration
@EnableSwagger
@ComponentScan(basePackages = {"com.demo.limits.rest",})
@Profile({"swagger"})
public class SwaggerConfig {
    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;

    @Resource
    private Environment env;

    @Bean
    public SwaggerSpringMvcPlugin v1limitsAPISwaggerSpringMvcPlugin() {
        return new SwaggerSpringMvcPlugin(springSwaggerConfig)
                .swaggerGroup("limits-v1-api")
                .includePatterns(
                        "/v1/.*"
                )
                .apiInfo(apiInfo())
                .pathProvider(apiSwaggerPathProvider())
                .directModelSubstitute(DateTime.class, String.class)
                .build();
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "limits",
                "360 Services",
                "http://developer.demo.com/Terms_of_service",
                "360@demo.com",
                "demo",
                "http://developer.demo.com/licenses/LICENSE-2.0.html"
        );
        return apiInfo;
    }

    @Bean
    public SwaggerPathProvider apiSwaggerPathProvider() {
        String docLocation = env.getRequiredProperty("swagger.docs");
        return new ApiSwaggerPathProvider(docLocation);
    }

    private class ApiSwaggerPathProvider extends SwaggerPathProvider {

        String docsLocation;

        @Autowired
        private ServletContext servletContext;

        private ApiSwaggerPathProvider(String docsLocation) {
            this.docsLocation = docsLocation;
        }

        @Override
        protected String applicationPath() {
            return getAppRoot()
                    .build()
                    .toString();
        }

        @Override
        protected String getDocumentationPath() {
            return getAppRoot()
                    .path(DefaultSwaggerController.DOCUMENTATION_BASE_PATH)
                    .build()
                    .toString();
        }

        private UriComponentsBuilder getAppRoot() {
            return UriComponentsBuilder.fromHttpUrl(docsLocation)
                    .path(servletContext.getContextPath());
        }
    }
}
