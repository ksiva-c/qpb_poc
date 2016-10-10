package com.demo.limits.core.config;

import org.springframework.context.annotation.*;

/**
 * Created by lnv.
 */
@Configuration
@PropertySources({
        @PropertySource(value = "classpath:application-core-test.properties"),
        @PropertySource(value = "file:${CONF_DIR}/application.properties", ignoreResourceNotFound = true)
})
public class TestCoreConfig {

}
