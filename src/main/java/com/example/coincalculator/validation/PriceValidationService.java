package com.example.coincalculator.validation;

import java.math.BigDecimal;

public interface PriceValidationService {

    void validateAmountAndType(String type, BigDecimal amount);
    void validateCoinType(String type, BigDecimal amount, String coinType);
}
