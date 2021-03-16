package com.example.coincalculator.service.impl;

import com.example.coincalculator.config.CacheClient;
import com.example.coincalculator.model.CurrencyWrapper;
import com.example.coincalculator.utils.CurrencyTypes;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class RefreshService {

    private final CacheClient cacheClient;
    private final CurrencyRetrieverService currencyRetrieverService;

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Duration duration = Duration.ofSeconds(10);

    public RefreshService(CacheClient cacheClient, CurrencyRetrieverService currencyRetrieverService) {
        this.currencyRetrieverService = currencyRetrieverService;
        this.cacheClient = cacheClient;
        loadCurrenciesToMap();
        refreshEvery10Seconds();
    }

     void refreshEvery10Seconds() {
        scheduler.scheduleAtFixedRate(() ->
                cacheClient.getMap().forEach((key, value) -> {
                            CurrencyWrapper wrapper = (CurrencyWrapper) value;
                            if (wrapper.getLastModified().isBefore(LocalDateTime.now().minus(duration))) {
                                currencyRetrieverService.getCurrencyRate(wrapper.getCurrency().getSymbol());
                            }
                        }
                ), 0, 10, TimeUnit.SECONDS);
    }

    public void loadCurrenciesToMap() {
        Arrays.asList(CurrencyTypes.values())
                .forEach(cur -> currencyRetrieverService.getCurrencyRate(cur.getSymbol()));
    }


}
