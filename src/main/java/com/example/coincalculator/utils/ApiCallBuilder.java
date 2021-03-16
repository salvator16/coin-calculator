package com.example.coincalculator.utils;

import org.springframework.web.util.UriComponentsBuilder;

public class ApiCallBuilder {

    public static final String TICKERS_RATE_URL = "https://api.blockchain.com/v3/exchange/tickers/%s";

    public static String tickersApiWithSymbol(String symbol) {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(String.format(TICKERS_RATE_URL,symbol));

        return builder.toUriString();
    }



}
