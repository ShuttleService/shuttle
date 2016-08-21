package com.real.apps.shuttle.valueObject;


import java.math.BigDecimal;

/**
 * Created by zorodzayi on 15/01/24.
 */
public class Money {
    private BigDecimal amount;
    private Currency currency;

    private Money() {
    }

    public Money(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return String.format("{amount:%s, Currency:%s}", amount, currency);
    }
}
