package com.real.apps.shuttle.valueObject;


/**
 * Created by zorodzayi on 15/01/24.
 */
public class Money {
    private double amount;
    private Currency currency;

    @Override
    public String toString() {
        return String.format("{amount:%d, Currency:%}", amount, currency);
    }
}
