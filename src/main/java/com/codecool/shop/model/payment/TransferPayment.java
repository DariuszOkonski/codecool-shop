package com.codecool.shop.model.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferPayment extends Payment{
    public TransferPayment(BigDecimal amountToPay, String userSessionId) {
        super(amountToPay, userSessionId);
    }

    @Override
    public void updateStatus() {
        System.out.println("Check status from shipping company");
    }


    public String getTitleForTransfer(){
        return getName() + LocalDateTime.now();
    }
}
