package com.codecool.shop.model.payment;

import java.math.BigDecimal;

public class PayPalPayment extends Payment{
    public PayPalPayment(BigDecimal amountToPay, String userSessionId) {
        super(amountToPay, userSessionId);
    }

    @Override
    public void updateStatus() {
        System.out.println("Get PayPal api status");
    }
}
