package com.real.apps.shuttle.valueObject;

/**
 * Created by zorodzayi on 15/04/08.
 */
public class Currency {
    private final String currencyCode;

    public Currency(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return String.format("{CurrencyCode:%s}", currencyCode);
    }
}