package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE,reason = "Car is overaged!")
public class InsuranceCarOveragedForInsuranceException extends RuntimeException {
    public InsuranceCarOveragedForInsuranceException(String message) {
        super(message);
    }
}
