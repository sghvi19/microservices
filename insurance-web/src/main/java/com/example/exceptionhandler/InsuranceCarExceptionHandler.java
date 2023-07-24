package com.example.exceptionhandler;

import com.example.exception.*;
import com.example.controller.InsuranceCarController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = InsuranceCarController.class)
public class InsuranceCarExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({InsuranceCarNotFoundException.class, CarNotFoundException.class})
    public ResponseEntity<Object> handleNoSuchElementException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    @ExceptionHandler(FranchiseExceededException.class)
    public ResponseEntity<Object> handleNoSuchElementException(FranchiseExceededException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(InsuranceCarOveragedForInsuranceException.class)
    public ResponseEntity<Object> handleNoSuchElementException(InsuranceCarOveragedForInsuranceException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(LicensePlateAlreadyExistsException.class)
    public ResponseEntity<Object> handleNoSuchElementException(LicensePlateAlreadyExistsException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
