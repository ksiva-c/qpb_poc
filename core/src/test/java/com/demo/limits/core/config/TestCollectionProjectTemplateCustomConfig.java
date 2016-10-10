package com.demo.limits.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Created by lnv.
 */
@Configuration
@PropertySources({
        @PropertySource(value = "classpath:application-core-test-disable-data-init.properties"),
        @PropertySource(value = "file:${CONF_DIR}/application.properties", ignoreResourceNotFound = true)
})
public class TestCollectionProjectTemplateCustomConfig {
}
