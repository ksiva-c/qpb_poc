package com.demo.limits.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.demo.limits.common.interceptors.ProfilingMethodInterceptor;
import com.demo.limits.common.utils.StringUtils;
import com.demo.limits.common.validation.*;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationInterceptor;

import javax.annotation.Resource;
import javax.validation.MessageInterpolator;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"com.demo.limits.common"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,pattern = ".*Test.*")
)
public class CommonConfig {

    private static final String PROPERTY_NAME_HIBERNATE_VALIDATOR_FAIL_FAST = "hibernate.validator.fail_fast";
    private static final String[] MODULES = {"common", "core", "domain", "durable-api",
                                            "idm", "repository", "services-support", "limits-360"};

    @Resource
    private Environment env;

    @Bean
    public CodeToMessageExpander codeToMessageExpander(){
        return new DefaultCodeToMessageExpander();
    }

    @Bean
    public Assertions assertions(CodeToMessageExpander codeToMessageExpander, Validator validator){
        //Since we are using LocalValidatoryFactoryBean, we can safely type cast is here
        return new Assertions((LocalValidatorFactoryBean)validator, codeToMessageExpander);
    }

    @Bean
    public LimitsMethodValidationPostProcessor methodValidationPostProcessor() {
        return new LimitsMethodValidationPostProcessor();
    }

    @Bean
    public MethodValidationInterceptor methodValidationInterceptor(){
        return new LimitsMethodValidationInterceptor();
    }

    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(messageInterpolator());
        validator.setValidationPropertyMap(validatorProperties());
        return validator;
    }

    private Map<String,String> validatorProperties() {
        Map<String,String> validatorProperties = new HashMap<>();
        validatorProperties.put(PROPERTY_NAME_HIBERNATE_VALIDATOR_FAIL_FAST, env.getProperty(PROPERTY_NAME_HIBERNATE_VALIDATOR_FAIL_FAST, Boolean.TRUE.toString()));
        return validatorProperties;
    }

    private MessageInterpolator messageInterpolator() {
        return new ResourceBundleMessageInterpolator(
                new AggregateResourceBundleLocator(Arrays.asList("common-validation-messages"))
        );
    }

    @Bean(name = "defaultJacksonMapper")
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper =  new ObjectMapper();
        JaxbAnnotationModule module = new JaxbAnnotationModule();
        objectMapper.registerModule(module);
        objectMapper.registerModule(new JodaModule());
        DateFormat dateFormat = new ISO8601DateFormat();
        objectMapper.setDateFormat(dateFormat);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Boolean indent = env.getProperty("indent.output", Boolean.class, false);
        if(indent) {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        }
        return objectMapper;
    }

    @Bean(name="messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        String[] baseNames = new String[MODULES.length];
        for(int i=0; i<MODULES.length; i++){
            baseNames[i] = "classpath:i18n/" + MODULES[i] + "-messages";
        }

        messageSource.setBasenames(baseNames);
        // if true, the key of the message will be displayed if the key is not
        // found, instead of throwing a NoSuchMessageException
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        // # -1 : never reload, 0 always reload
        messageSource.setCacheSeconds(0);
        return messageSource;
    }

    @Bean
    public NameMatchMethodPointcutAdvisor profilingAdvisor(Assertions assertions, StringUtils stringUtils){
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        advisor.setMappedName("*");
        advisor.setAdvice(new ProfilingMethodInterceptor(assertions, stringUtils));
        return advisor;
    }

    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator(){
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setBeanNames("*Template");
        creator.setInterceptorNames("profilingAdvisor");
        return creator;
    }
}
