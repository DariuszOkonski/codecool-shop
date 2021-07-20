package com.codecool.shop.model.payment;

import java.math.BigDecimal;

public enum PaymentMethods {
    CREDIT_CARD("credit-card"),
    PAY_PAL("paypal"),
    TRANSFER("transfer");

    public String friendlyName;


    public final String url;
    PaymentMethods(String url) {
        this.url = url;
    }

    public static PaymentMethod build(String url, BigDecimal amountToPay, String userSessionId){
        switch(url) {
            case "credit-card":
                return new CreditCardPayment(amountToPay, userSessionId);
            case "transfer":
                return new TransferPayment(amountToPay, userSessionId);
            case "paypal":
                return new PayPalPayment(amountToPay, userSessionId);
            default:
                return null;
        }
    }
    public String getUrl(){
        return url;
    }

    public static PaymentMethods valueOfUrl(String url){
        switch(url) {
            case "credit-card":
                return CREDIT_CARD;
            case "transfer":
                return TRANSFER;
            case "paypal":
                return PAY_PAL;
            default:
                return null;
        }
    }
    public String getFriendlyName(){
        switch(this) {
            case CREDIT_CARD:
                return "Credit card";
            case TRANSFER:
                return "Regular transfer";
            case  PAY_PAL:
                return "PayPal";
            default:
                return null;
        }
    }


}
