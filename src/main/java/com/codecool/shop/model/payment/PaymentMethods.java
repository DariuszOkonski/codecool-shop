package com.codecool.shop.model.payment;

import java.math.BigDecimal;

public enum PaymentMethods {
    CREDIT_CARD("Credit card"),
    PAY_PAL("PayPal"),
    TRANSFER("Regular transfer");

    public final String friendlyName;
    public String url;
    PaymentMethods(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public PaymentMethod build(BigDecimal amountToPay, String userSessionId){
        switch(this) {
            case CREDIT_CARD:
                return new CreditCardPayment(amountToPay, userSessionId);
            case TRANSFER:
                return new TransferPayment(amountToPay, userSessionId);
            case PAY_PAL:
                return new PayPalPayment(amountToPay, userSessionId);
            default:
                return null;
        }
    }
    public String getUrlString(){
        switch(this) {
            case CREDIT_CARD:
                return "credit-card";
            case PAY_PAL:
                return "paypal";
            case TRANSFER:
                return "transfer";
            default:
                return "";
        }
    }

}
