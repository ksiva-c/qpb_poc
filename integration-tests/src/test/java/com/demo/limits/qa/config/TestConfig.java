package com.demo.limits.qa.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.demo.limits.qa.spring.TestHttpRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lnv.
 */
@Configuration
public class TestConfig {
    @Bean(name = "restTemplate")
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        configureMessageConverters(converters);
        restTemplate.setMessageConverters(converters);
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new TestHttpRequestInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        JaxbAnnotationModule module = new JaxbAnnotationModule();
        converter.getObjectMapper().registerModule(module);
        converter.getObjectMapper().registerModule(new JodaModule());
        DateFormat dateFormat = new ISO8601DateFormat();
        converter.getObjectMapper().setDateFormat(dateFormat);
        converter.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        converter.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        converter.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        converters.add(converter);

        Jaxb2RootElementHttpMessageConverter
                jaxb2RootElementHttpMessageConverter = new Jaxb2RootElementHttpMessageConverter();
        List<MediaType> jaxbMediaTypes = Arrays.asList(
                new MediaType("application", "xml", Charset.forName("UTF-8")),
                new MediaType("application", "*+xml", Charset.forName("UTF-8"))
        );
        jaxb2RootElementHttpMessageConverter.setSupportedMediaTypes(jaxbMediaTypes);
        converters.add(jaxb2RootElementHttpMessageConverter);

    }

    @Bean(name = "defaultJacksonMapper")
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper =  new ObjectMapper();
        JaxbAnnotationModule module = new JaxbAnnotationModule();
        objectMapper.registerModule(module);
        objectMapper.registerModule(new JodaModule());
        DateFormat dateFormat = new ISO8601DateFormat();
        objectMapper.setDateFormat(dateFormat);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper;
    }
}
