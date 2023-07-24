package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,reason = "license plate already exists!")
public class LicensePlateAlreadyExistsException extends RuntimeException{
    public LicensePlateAlreadyExistsException(String message) {
        super(message);
    }
}
