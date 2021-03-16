package com.example.coincalculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AmountNotSufficientOrExceedException extends RuntimeException {
    public AmountNotSufficientOrExceedException(final String message) { super(message);}
}
