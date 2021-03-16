package com.example.coincalculator.service.impl;

import com.example.coincalculator.config.CacheClient;
import com.example.coincalculator.model.CurrencyResponse;
import com.example.coincalculator.model.CurrencyWrapper;
import com.example.coincalculator.service.PriceCalculationService;
import com.example.coincalculator.utils.CoinTypes;
import com.example.coincalculator.utils.CurrencyTypes;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PriceCalculationImpl implements PriceCalculationService {

    private CacheClient cacheClient;

    public PriceCalculationImpl(CacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }

    @Override
    public CurrencyResponse calculatePriceWithAmount(String type, BigDecimal amount) {
        CurrencyWrapper wrapper = cacheClient.get(CurrencyTypes.valueOf(type).getSymbol());
        return CurrencyResponse.builder()
                .coinAmount(amount.divide(wrapper.getCurrency().getLast_trade_price(),
                        8, RoundingMode.CEILING))
                .modifiedDate(wrapper.getLastModified())
                .coinType(CoinTypes.BTC.getName())
                .type(type)
                .amount(amount)
                .build();
    }

    @Override
    public CurrencyResponse calculatePriceWithCoinAmount(String coinType, BigDecimal coinAmount, String currencyType) {
        CurrencyWrapper wrapper = cacheClient.get(CurrencyTypes.valueOf(currencyType).getSymbol());
        return CurrencyResponse.builder()
                .amount(coinAmount.multiply(wrapper.getCurrency().getLast_trade_price()))
                .modifiedDate(wrapper.getLastModified())
                .coinType(coinType)
                .type(currencyType)
                .coinAmount(coinAmount)
                .build();
    }
}
