package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Insurance car not found!")
public class InsuranceCarNotFoundException extends RuntimeException{
    public InsuranceCarNotFoundException(String message) {
        super(message);
    }
}
