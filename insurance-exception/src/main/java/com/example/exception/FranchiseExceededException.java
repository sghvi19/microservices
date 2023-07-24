package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Franchise not acceptable!")
public class FranchiseExceededException extends RuntimeException{
    public FranchiseExceededException(String message) {
        super(message);
    }
}
