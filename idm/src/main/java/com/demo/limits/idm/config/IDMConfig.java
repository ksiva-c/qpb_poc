package com.demo.limits.idm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Created by lnv.
 */
@Configuration
@ComponentScan(basePackages = {"com.demo.limits.idm"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,pattern = ".*Test.*")
)
public class IDMConfig {

}
