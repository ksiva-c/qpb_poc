package com.demo.limits.common.validation;

import java.util.Map;

public class BaseException extends RuntimeException {

    public static final String CODE = "BASE_EXCEPTION";
    public static final String FORMAT = "###";

    private String code;
    private Object[] args;
    private Map<String, Object> argsMap;

    private String methodInfo;

    private String helpText;

    public BaseException() {
        this.code = CODE;
    }

	public BaseException(String code, Object[] args, String message) {
		super(message);
        this.code = code;
        this.args = args;
	}

	public BaseException(String code, Object[] args, Throwable cause) {
		super(cause);
        this.code = code;
        this.args = args;
    }

	public BaseException(String code, Object[] args, String message, Throwable cause) {
		super(message, cause);
        this.code = code;
        this.args = args;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public Map<String, Object> getArgsMap() {
        return argsMap;
    }

    public void setArgsMap(Map<String, Object> argsMap) {
        this.argsMap = argsMap;
    }

    public String getMethodInfo() {
        return methodInfo;
    }

    public void setMethodInfo(String methodInfo) {
        this.methodInfo = methodInfo;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public String getHelpText(){
        return helpText;
    }
}
