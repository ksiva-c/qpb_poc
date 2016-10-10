package com.demo.limits.common.validation;

import com.demo.limits.common.context.ContextHolder;
import org.springframework.context.MessageSource;

import javax.inject.Inject;
import java.util.Locale;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
public class DefaultCodeToMessageExpander implements CodeToMessageExpander {

    @Inject
    MessageSource messageSource;

    @Override
    public String get(String code) {
        //TODO: Later to use ContextHolder.get().getRequestContext().getLocaleId() to construct the locale
        Locale javaLocale = Locale.getDefault();
        return messageSource.getMessage(code, null, javaLocale);
    }

    @Override
    public String get(String code, String defaultVal) {
        //TODO: Later to use ContextHolder.get().getRequestContext().getLocaleId() to construct the locale
        Locale javaLocale = Locale.getDefault();
        return messageSource.getMessage(code, null, defaultVal, javaLocale);
    }
}
