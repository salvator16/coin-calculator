package com.example.coincalculator.utils;

public enum CoinTypes {

    BTC("BTC");

    private String name;

    private CoinTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
