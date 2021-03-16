package com.example.coincalculator.exception.handler;

import com.example.coincalculator.exception.AmountNotSufficientOrExceedException;
import com.example.coincalculator.exception.CoinTypeNotSupportedException;
import com.example.coincalculator.exception.CurrencyRateNotFoundException;
import com.example.coincalculator.exception.CurrencyTypeNotSupportedException;
import com.example.coincalculator.exception.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CoinCalculatorExceptionHandler {

    /**
     *  HTTP status code BAD REQUEST (502)
     *
     * @param e Exception to be handled
     * @return {@link ErrorDto} containing the error message
     */
    @ExceptionHandler
    public ResponseEntity<ErrorDto> currencyTypeNotSupportedException(CurrencyTypeNotSupportedException e) {
        final ErrorDto errorDto = new ErrorDto(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    /**
     *  HTTP status code BAD REQUEST (502)
     *
     * @param e Exception to be handled
     * @return {@link ErrorDto} containing the error message
     */
    @ExceptionHandler
    public ResponseEntity<ErrorDto> amountNotSufficientOrExceededException(AmountNotSufficientOrExceedException e) {
        final ErrorDto errorDto = new ErrorDto(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    /**
     *  HTTP status code NOT_FOUND (404)
     *
     * @param e Exception to be handled
     * @return {@link ErrorDto} containing the error message
     */
    @ExceptionHandler
    public ResponseEntity<ErrorDto> currencyRateNotFoundedExcepiton(CurrencyRateNotFoundException e) {
        final ErrorDto errorDto = new ErrorDto(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    /**
     *  HTTP status code NOT_ACCEPTABLE (406)
     *
     * @param e Exception to be handled
     * @return {@link ErrorDto} containing the error message
     */
    @ExceptionHandler
    public ResponseEntity<ErrorDto> coinTypeNotSupportedExcepiton(CoinTypeNotSupportedException e) {
        final ErrorDto errorDto = new ErrorDto(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorDto);
    }

}
