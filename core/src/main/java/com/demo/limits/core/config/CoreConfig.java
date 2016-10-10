package com.demo.limits.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Created by lnv.
 */
@Configuration
@ComponentScan(
        basePackages = {"com.demo.limits.core"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,pattern = ".*Test.*")
)
public class CoreConfig {

}
