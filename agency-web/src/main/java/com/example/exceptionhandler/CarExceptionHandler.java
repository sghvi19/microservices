package com.example.exceptionhandler;

import com.example.exception.*;
import com.example.controller.CarController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = CarController.class)
public class CarExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({CarNotFoundException.class, PersonNotFoundException.class, OwnerNotFoundException.class})
    public ResponseEntity<Object> handleNoSuchElementException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(LicensePlateAlreadyExistsException.class)
    public ResponseEntity<Object> handleNoSuchElementException(LicensePlateAlreadyExistsException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(VinCodeAlreadyExistsException.class)
    public ResponseEntity<Object> handleNoSuchElementException(VinCodeAlreadyExistsException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}