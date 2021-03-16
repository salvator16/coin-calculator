package com.example.coincalculator.validation;

import com.example.coincalculator.exception.AmountNotSufficientOrExceedException;
import com.example.coincalculator.exception.CoinTypeNotSupportedException;
import com.example.coincalculator.exception.CurrencyRateNotFoundException;
import com.example.coincalculator.utils.CoinTypes;
import com.example.coincalculator.utils.CurrencyTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class PriceValidationServiceImpl implements PriceValidationService {

    private static final Logger log = LoggerFactory.getLogger(PriceValidationServiceImpl.class);

    @Override
    public void validateAmountAndType(String type, BigDecimal amount) {
        validateCurrencyType(type);

        if (amount.compareTo(BigDecimal.valueOf(25L)) < 0 || amount.compareTo(BigDecimal.valueOf(50000L)) > 0) {
            log.error("Price out of range exception %d", amount);
            throw new AmountNotSufficientOrExceedException("Amount should be between 25 or 50000");
        }
    }

    @Override
    public void validateCoinType(String type, BigDecimal coinAmount, String coinType) {
        validateCurrencyType(type);
        if (!CoinTypes.BTC.getName().equals(coinType)) {
            throw new CoinTypeNotSupportedException("Only BTC allowed");
        }
    }

    void validateCurrencyType(String type) {
        Arrays.stream(CurrencyTypes.values())
                .filter(el -> el.getName().equals(type))
                .findFirst()
                .orElseThrow(() -> new CurrencyRateNotFoundException(String.format("Currency not supported %s", type)));

    }

}
