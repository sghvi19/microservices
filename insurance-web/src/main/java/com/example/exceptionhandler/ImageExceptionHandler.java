package com.example.exceptionhandler;

import com.example.controller.InsuranceCarController;
import com.example.exception.ImageNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = InsuranceCarController.class)
public class ImageExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<Object> handleNoSuchElementException(ImageNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
