package com.demo.limits.common.validation;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
public interface CodeToMessageExpander {
    String get(String code);
    String get(String code, String defaultVal);
}
