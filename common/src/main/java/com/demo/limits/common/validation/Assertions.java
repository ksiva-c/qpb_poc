package com.demo.limits.common.validation;


import com.demo.limits.common.utils.CollectionUtils;
import org.antlr.stringtemplate.StringTemplate;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

public class Assertions {

	private CodeToMessageExpander messageExpander;
    private Validator validator;


    private static final String FACTORY_ERROR = "Assertions Error.";

    private static final String DEFAULT_FORMAT = "###";
    private static final String DEFAULT_CODE = "DEFAULT_EXCEPTION_CODE";

    public Assertions(Validator validator, CodeToMessageExpander messageExpander){
        this.validator = validator;
        this.messageExpander = messageExpander;
    }

    public void validate(Object value) throws BaseException {
        this.validate(value, BaseValidationException.class);
    }

    public void validate(Object value, Class<? extends ValidationExceptionFactory> exceptionFactory, Class<?>... groups) throws BaseException {
        if (value == null){
            throw new NullValueException();
        }
        final Set<ConstraintViolation<Object>>  violations = validator.validate(value, groups);
        if(!violations.isEmpty()){
            try {
                throw exceptionFactory.newInstance().create(new ConstraintViolationException(ValidationHelper.toMessage(violations), violations));
            } catch (InstantiationException e) {
                throw new BaseException(FACTORY_ERROR, new Object[0], FACTORY_ERROR, e);
            } catch (IllegalAccessException e) {
                throw new BaseException(FACTORY_ERROR, new Object[0], FACTORY_ERROR, e);
            }
        }
    }

    public void notNull(Object value) throws BaseException {
        if (value == null) notNull(value, BaseException.class);
    }

    public void notNull(Object value, Class<? extends BaseException> clazz) throws BaseException {
        if (value == null) notNull(value, clazz, getDefaultCode(clazz));
    }

    public void notNull(Object value, Class<? extends BaseException> clazz, String code) throws BaseException {
        if (value == null) notNull(value, clazz, code, null);
    }

    public void notNull(Object value, Class<? extends BaseException> clazz, Object[] args) throws BaseException {
        if (value == null) notNull(value, clazz, getDefaultCode(clazz), args);
    }

    public void notNull(Object value, Class<? extends BaseException> clazz, String code, Object[] args) throws BaseException {
        isTrue((value != null), clazz, code, args);
    }

    public void isTrue(Boolean expr) throws BaseException {
        if (!expr) isTrue(expr, BaseException.class);
    }

    public void isTrue(Boolean expr, Class<? extends BaseException> clazz) throws BaseException {
        if (!expr) isTrue(expr, clazz, getDefaultCode(clazz));
    }

    public void isTrue(Boolean expr, Class<? extends BaseException> clazz, String code) throws BaseException {
        if (!expr) isTrue(expr, clazz, code, null);
	}

    public void isTrue(Boolean expr, Class<? extends BaseException> clazz, Object[] args) throws BaseException {
        if (!expr) isTrue(expr, clazz, getDefaultCode(clazz), args);
    }

    public void isTrue(Boolean expr, Class<? extends BaseException> clazz, String code, Object[] args) throws BaseException {
        if (expr) return;
        String message = format(clazz, code, args);
        BaseException exception = newInstance(clazz, code, args, message, null);
        throw exception;
    }

    public void throwEx(Class<? extends BaseException> clazz) throws BaseException {
        throwEx(clazz, getDefaultCode(clazz));
    }

    public void throwEx(Class<? extends BaseException> clazz, String code) throws BaseException {
        throwEx(clazz, code, null);
    }

    public void throwEx(Class<? extends BaseException> clazz, Object[] args) throws BaseException {
        throwEx(clazz, getDefaultCode(clazz), args);
    }

    public void throwEx(Class<? extends BaseException> clazz, String code, Object[] args) throws BaseException {
        String message = format(clazz, code, args);
        throw newInstance(clazz, code, args, message, null);
    }

    public BaseException wrap(Throwable ex) throws BaseException {
        return wrap(ex, BaseException.class);
    }

    public BaseException wrap(Throwable ex, Class<? extends BaseException> clazz) throws BaseException {
        return wrap(ex, clazz, getDefaultCode(clazz));
    }

    public BaseException wrap(Throwable ex, Class<? extends BaseException> clazz, String code) throws BaseException {
        return wrap(ex, clazz, code, null);
    }

    public BaseException wrap(Throwable ex, Class<? extends BaseException> clazz, Object[] args) throws BaseException {
        return wrap(ex, clazz, getDefaultCode(clazz), args);
    }

    public BaseException wrap(Throwable ex, Class<? extends BaseException> clazz, String code, Object[] args) throws BaseException {
        String message = format(clazz, code, args);
        return newInstance(clazz, code, args, message, ex);
    }

    public BaseException createEx(Class<? extends BaseException> clazz) throws BaseException {
        return createEx(clazz, getDefaultCode(clazz));
    }

    public BaseException createEx(Class<? extends BaseException> clazz, String code) throws BaseException {
        return createEx(clazz, code, null);
    }

    public BaseException createEx(Class<? extends BaseException> clazz, Object[] args) throws BaseException {
        return createEx(clazz, getDefaultCode(clazz), args);
    }

    public BaseException createEx(Class<? extends BaseException> clazz, String code, Object[] args) throws BaseException {
        String message = format(clazz, code, args);
        return newInstance(clazz, code, args, message, null);
    }

    private String format(Class<? extends BaseException> clazz, String code){
        return format(clazz, code, null);
    }
    private String format(Class<? extends BaseException> clazz, String code, Object[] args){
        String pattern = messageExpander.get(code, "");
        if (pattern.length() == 0){
            //Nothing found against the code provided,
            //let us check if we can get it against the default code in the class
            pattern = messageExpander.get(getDefaultCode(clazz), getDefaultFormat(clazz));
        }
        //String pattern = messageExpander.get(getDefaultCode(clazz), getDefaultFormat(clazz));
        Map<String, Object> argsMap = CollectionUtils.arrayToMap(Object.class, args);
        if (argsMap == null || argsMap.isEmpty()){
            return pattern;
        }
        if (BaseException.FORMAT.equals(pattern)){
            return argsMap.toString();
        }
        StringTemplate template = new StringTemplate(pattern);
        template.setArgumentContext(argsMap);
        String message = template.toString();
        return message;
    }

    private BaseException newInstance(Class<? extends BaseException> clazz, String code, Object[] args, String message, Throwable ex){
        try {
            BaseException newInstance = null;
            if (message.length() != 0){
                if (ex != null){
                    //Get constructor with both args String, Throwable
                    Constructor<? extends BaseException> constructor = clazz.getConstructor(new Class<?>[]{String.class, Object[].class, String.class, Throwable.class});
                    newInstance = constructor.newInstance(code, args, message, ex);
                }
                else{
                    //Get constructor with args String
                    Constructor<? extends BaseException> constructor = clazz.getConstructor(new Class<?>[]{String.class, Object[].class, String.class});
                    newInstance = constructor.newInstance(code, args, message);
                }
            }
            else {
                if (ex != null){
                    //Get constructor with args Throwable
                    Constructor<? extends BaseException> constructor = clazz.getConstructor(new Class<?>[]{String.class, Object[].class, Throwable.class});
                    newInstance = constructor.newInstance(code, args, ex);
                }
                else{
                    //Get constructor with NO args
                    newInstance = clazz.newInstance();
                }
            }
            newInstance.setArgsMap(CollectionUtils.arrayToMap(Object.class, args));
            if (code != null){
                String helpText = messageExpander.get(code + "$HELP", "");
                newInstance.setHelpText(helpText);
            }
            return newInstance;
        } catch (InstantiationException e) {
            throw new BaseException(FACTORY_ERROR, args, FACTORY_ERROR, e);
        } catch (IllegalAccessException e) {
            throw new BaseException(FACTORY_ERROR, args, FACTORY_ERROR, e);
        } catch (NoSuchMethodException e) {
            throw new BaseException(FACTORY_ERROR, args, FACTORY_ERROR, e);
        } catch (InvocationTargetException e) {
            throw new BaseException(FACTORY_ERROR, args, FACTORY_ERROR, e);
        }
    }

    private String getDefaultFormat(Class<? extends BaseException> clazz){
        return (String)getStaticFieldValue(clazz, "FORMAT", DEFAULT_FORMAT);
    }

    private String getDefaultCode(Class<? extends BaseException> clazz){
        return (String)getStaticFieldValue(clazz, "CODE", DEFAULT_CODE);
    }

    private Object getStaticFieldValue(Class<? extends BaseException> clazz, String fieldName, Object defaultValue) throws BaseException {
        try {
            Field codeField = clazz.getField(fieldName);
            return (String)codeField.get(null);
        } catch (NoSuchFieldException e) {
            return defaultValue;
        } catch (IllegalAccessException e) {
            throw new BaseException(FACTORY_ERROR, new Object[]{"fieldName", fieldName}, FACTORY_ERROR, e);
        }
    }
}
