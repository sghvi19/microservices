package com.example.web.exceptionhandler;

import com.example.web.controller.PersonController;
import dev.omedia.exception.PersonNotFoundException;
import dev.omedia.exception.PersonalNoAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = PersonController.class)
public class PersonExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<Object> handleNoSuchElementException(PersonNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(PersonalNoAlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistsElementException(PersonalNoAlreadyExistsException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}