package com.example.coincalculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CoinTypeNotSupportedException extends RuntimeException {
    public CoinTypeNotSupportedException(final String message) { super(message);}
}
