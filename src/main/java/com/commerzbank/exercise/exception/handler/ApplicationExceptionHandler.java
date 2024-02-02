package com.commerzbank.exercise.exception.handler;

import com.commerzbank.exercise.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGeneralExceptions(Exception ex, HttpServletRequest re) {
        return buildResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR, re);
    }

    private ResponseEntity<Object> buildResponseEntity(Exception ex, HttpStatus httpStatus, HttpServletRequest re) {
        var apiError = ErrorResponse
                .builder()
                .path(re.getRequestURI())
                .status(httpStatus)
                .message(ex.getMessage())
                .errorClass(ex.getClass().getSimpleName())
                .build();
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
