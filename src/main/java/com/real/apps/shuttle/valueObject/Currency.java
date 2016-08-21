package com.real.apps.shuttle.valueObject;

/**
 * Created by zorodzayi on 15/04/08.
 */
public class Currency {
    private String currencyCode;

    private Currency() {
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Currency(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return String.format("{CurrencyCode:%s}", currencyCode);
    }
}