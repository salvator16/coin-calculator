package com.example.coincalculator.service;

import com.example.coincalculator.model.CurrencyResponse;

import java.math.BigDecimal;

public interface PriceCalculationService {

    CurrencyResponse calculatePriceWithAmount(String type, BigDecimal amount);

    CurrencyResponse calculatePriceWithCoinAmount(String coinType, BigDecimal coinAmount, String currencyType);

}
