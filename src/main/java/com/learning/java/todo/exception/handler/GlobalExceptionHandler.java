package com.learning.java.todo.exception.handler;

import com.learning.java.todo.exception.DateException;
import com.learning.java.todo.exception.TodoException;
import com.learning.java.todo.exception.UserException;
import com.learning.java.todo.model.Error;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {UserException.class, TodoException.class, DateException.class})
    protected ResponseEntity<Object> handleConflict(UserException ex, WebRequest request) {
        String des=  ex.getDescription();
        String code=  ex.getErrorCode();
        System.out.println(ex.getDescription());
        Error error = Error.builder().errorCode(ex.getErrorCode()).description(ex.getDescription()).build();
        System.out.println(error.getErrorCode());
        return handleExceptionInternal(ex, error,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
