package com.codecool.shop.model.payment;

import java.math.BigDecimal;

public class PayPalPayment extends Payment{
    public PayPalPayment(BigDecimal amountToPay, String methodName, int ordId) {
        super(amountToPay, methodName, ordId);
    }

    @Override
    public void updateStatus() {
        System.out.println("Get PayPal api status");
    }
}
