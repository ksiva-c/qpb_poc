package com.demo.limits.rest.exceptions;


import com.demo.limits.core.exception.*;
import com.demo.limits.common.validation.BaseException;
import com.demo.limits.common.validation.BaseValidationException;
import com.demo.limits.common.validation.OperationNotSupportedException;
import com.demo.limits.model.common.ApiError;
import com.demo.limits.repository.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionMapper {
    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionMapper.class);

    // Catch All Exception
    @ExceptionHandler({
            Exception.class,
    })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ApiError handleRequestException(Exception exception) {
        logger.error("ErrorMessage = {} ", exception.getLocalizedMessage(),exception);
        String code = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        return new ApiError(exception.getLocalizedMessage(),code);
    }

    // Base Exception
    @ExceptionHandler({
            BaseException.class,
    })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ApiError handleRequestException(BaseException exception) {
        logger.error("ErrorMessage = {} ", exception.getLocalizedMessage(),exception);
        String code = exception.getCode();
        return new ApiError(exception.getLocalizedMessage(),code);
    }

    // Repository Exception
    @ExceptionHandler({
            EntityNotFoundException.class,
    })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ApiError handleRequestException(EntityNotFoundException exception) {
        logger.error("ErrorMessage = {} ", exception.getLocalizedMessage(),exception);
        String code = exception.getCode();
        return new ApiError(exception.getLocalizedMessage(),code);
    }


    /**
     * Send a 409 Conflict in case of concurrent modification
     */
    @ExceptionHandler({
            ObjectOptimisticLockingFailureException.class,
            OptimisticLockingFailureException.class,
            DataIntegrityViolationException.class,
            IntegrityViolationException.class
    })
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody ApiError handleConflict(Exception exception) {
        logger.error("ErrorMessage = {} ", exception.getLocalizedMessage(),exception);
        String code =  HttpStatus.CONFLICT.getReasonPhrase();
        return new ApiError(exception.getLocalizedMessage(),code);
    }

    @ExceptionHandler({
            EntityDuplicateException.class,
    })
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody ApiError handleRequestException(EntityDuplicateException exception) {
        logger.error("ErrorMessage = {} ", exception.getLocalizedMessage(),exception);
        String code = exception.getCode();
        return new ApiError(exception.getLocalizedMessage(),code);
    }

    @ExceptionHandler({
            CollectionDuplicateException.class,
    })
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody ApiError handleRequestException(CollectionDuplicateException exception) {
        logger.error("ErrorMessage = {} ", exception.getLocalizedMessage(),exception);
        String code = exception.getCode();
        return new ApiError(exception.getLocalizedMessage(),code);
    }

    @ExceptionHandler({
            RelatedEntityNotFoundException.class,
    })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ApiError handleRequestException(RelatedEntityNotFoundException exception) {
        logger.error("ErrorMessage = {} ", exception.getLocalizedMessage(),exception);
        String code = exception.getCode();
        return new ApiError(exception.getLocalizedMessage(),code);
    }

    // Spring DataAccess exceptions
    @ExceptionHandler({
            DataAccessException.class
    })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ApiError handleRequestException(DataAccessException exception) {
        logger.error("ErrorMessage = {} ", exception.getLocalizedMessage(),exception);
        String code = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        return new ApiError(exception.getLocalizedMessage(),code);
    }




    // Collection Exception
    @ExceptionHandler({
            CoreException.class,
    })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ApiError handleRequestException(CoreException exception) {
        logger.error("ErrorMessage = {} ", exception.getLocalizedMessage(),exception);
        String code = exception.getCode();
        return new ApiError(exception.getLocalizedMessage(),code);
    }



    @ExceptionHandler({
            CollectionValidationException.class,
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ApiError handleRequestException(CollectionValidationException exception) {
        logger.error("ErrorMessage = {} ", exception.getLocalizedMessage(),exception);
        String code = exception.getCode();
        return new ApiError(exception.getLocalizedMessage(), code);
    }


    @ExceptionHandler({
            BaseValidationException.class,
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ApiError handleRequestException(BaseValidationException exception) {
        logger.error("ErrorMessage = {} ", exception.getLocalizedMessage(),exception);
        String code = exception.getCode();
        return new ApiError(exception.getLocalizedMessage(), code);
    }


    @ExceptionHandler({
            EntityNameDuplicateException.class,
    })
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody ApiError handleRequestException(EntityNameDuplicateException exception) {
        logger.error("ErrorMessage = {} ", exception.getLocalizedMessage(),exception);
        String code = exception.getCode();
        return new ApiError(exception.getLocalizedMessage(),code);
    }



    @ExceptionHandler({
            CollectionNotFoundException.class
    })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ApiError handleRequestException(CollectionNotFoundException exception) {
        logger.error("ErrorMessage = {} ", exception.getLocalizedMessage(),exception);
        String code = exception.getCode();
        return new ApiError(exception.getLocalizedMessage(),code);
    }



    @ExceptionHandler({
            OperationNotSupportedException.class
    })
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public @ResponseBody ApiError handleRequestException(OperationNotSupportedException exception) {
        logger.error("ErrorMessage = {} ", exception.getLocalizedMessage(),exception);
        String code = exception.getCode();
        return new ApiError(exception.getLocalizedMessage(),code);
    }



}
