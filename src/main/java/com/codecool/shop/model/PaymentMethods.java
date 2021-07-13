package com.codecool.shop.model;

public enum PaymentMethods {
    CREDIT_CARD("Credit card"),
    PAY_PAL("PayPal"),
    AFTER_SHIPPING("After the shipping");

    public final String friendlyName;
    PaymentMethods(String friendlyName) {
        this.friendlyName = friendlyName;
    }
}
