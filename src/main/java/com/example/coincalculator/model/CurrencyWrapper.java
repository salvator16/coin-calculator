package com.example.coincalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@With
@AllArgsConstructor
public class CurrencyWrapper implements Serializable {

    private final Currency currency;
    private final LocalDateTime lastModified;

    public CurrencyWrapper(final Currency currency) {
        this.currency = currency;
        this.lastModified = LocalDateTime.now();
    }

}
