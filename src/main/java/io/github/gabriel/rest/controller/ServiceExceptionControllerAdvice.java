package io.github.gabriel.rest.controller;

import io.github.gabriel.core.base.exception.service.InvalidCodeException;
import io.github.gabriel.core.base.exception.service.NoDataException;
import io.github.gabriel.core.base.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceExceptionControllerAdvice {

    @ExceptionHandler(InvalidCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleInvalidCodeException(InvalidCodeException ex) {
        return new ApiErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(NoDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleNoDataException(NoDataException ex) {
        return new ApiErrorResponse(ex.getCode(), ex.getMessage());
    }
}
