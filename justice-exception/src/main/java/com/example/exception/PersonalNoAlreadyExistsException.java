package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Personal number already exists!")
public class PersonalNoAlreadyExistsException extends RuntimeException {
    public PersonalNoAlreadyExistsException(String message) {
        super(message);
    }
}