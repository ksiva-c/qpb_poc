package com.demo.limits.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lnv.
 */
public class ApiError {
    @JsonProperty
    private String errorMessage;
    @JsonProperty
    private String code;

    public ApiError(String errorMessage, String code) {
        this.errorMessage = errorMessage;
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getCode() {
        return code;
    }
}
