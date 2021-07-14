package com.codecool.shop.model.payment;

import java.math.BigDecimal;

public class CreditCardPayment extends Payment{
    public CreditCardPayment(BigDecimal amountToPay, String userSessionId) {
        super(amountToPay, userSessionId);
    }

    @Override
    public void updateStatus() {
        System.out.println("Check status from Credit Card payment");
    }
}
