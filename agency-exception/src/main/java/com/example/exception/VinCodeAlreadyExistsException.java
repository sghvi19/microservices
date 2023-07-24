package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "vin code not found!")
public class VinCodeAlreadyExistsException extends RuntimeException{
    public VinCodeAlreadyExistsException(String message) {
        super(message);
    }
}
