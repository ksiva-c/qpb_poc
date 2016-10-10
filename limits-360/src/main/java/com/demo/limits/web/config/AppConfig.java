package com.demo.limits.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 
 */

@Configuration
@ComponentScan(basePackages = {"com.demo.limits.rest","com.demo.limits.web"})
public class AppConfig {

//    @Bean(name="messageSource")
//    public MessageSource messageSource() {
//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasenames("classpath:i18n/messages");
//        // if true, the key of the message will be displayed if the key is not
//        // found, instead of throwing a NoSuchMessageException
//        messageSource.setUseCodeAsDefaultMessage(true);
//        messageSource.setDefaultEncoding("UTF-8");
//        // # -1 : never reload, 0 always reload
//        messageSource.setCacheSeconds(0);
//        return messageSource;
//    }
}
