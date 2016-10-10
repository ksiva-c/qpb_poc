package com.demo.limits.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * User: lnv
 * Date: 
 * Time: 
 */

@Configuration
@PropertySources({
        @PropertySource(value = "classpath:application-common-test.properties"),
        @PropertySource(value = "file:${CONF_DIR}/application.properties", ignoreResourceNotFound = true)
})
public class TestCommonConfig {
}
