package com.codecool.shop.model.payment;

import java.math.BigDecimal;

public class CreditCardPayment extends Payment{
    private CreditCard card;


    public CreditCardPayment(BigDecimal amountToPay, String userSessionId) {
        super(amountToPay, userSessionId);
    }

    public void setCard(CreditCard card) {
        this.card = card;
    }

    @Override
    public void updateStatus() {
        setFinished(card.isDataCorrect());
    }

}
