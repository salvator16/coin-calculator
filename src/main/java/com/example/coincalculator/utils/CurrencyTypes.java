package com.example.coincalculator.utils;

public enum CurrencyTypes {

    USD("BTC-USD", "USD"),
    EUR("BTC-EUR", "EUR");

    private String symbol;
    private String name;

    private CurrencyTypes(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }



}
