package com.example.coincalculator.service.impl;

import com.example.coincalculator.config.CacheClient;
import com.example.coincalculator.model.Currency;
import com.example.coincalculator.model.CurrencyWrapper;
import com.example.coincalculator.utils.ApiCallBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CurrencyRetrieverService {

    private final RestTemplate restTemplate;
    private final CacheClient cacheClient;

    @Async("executor")
    public CurrencyWrapper getCurrencyRate(String symbol)  {
        Object response = restTemplate.getForObject(ApiCallBuilder.tickersApiWithSymbol(symbol), Object.class);

        ObjectMapper mapper = new ObjectMapper();
        Currency currency = mapper.convertValue(response, Currency.class);
        CurrencyWrapper wrapper = new CurrencyWrapper(currency);

        cacheClient.put(symbol, wrapper);

        return wrapper;
    }



}
