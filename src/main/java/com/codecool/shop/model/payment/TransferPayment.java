package com.codecool.shop.model.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferPayment extends Payment{
    private final String transferTitle;
    private final String companyAccountNumber = "123412341234123412341234";
    private final String companyName = "Codecool Sp. z o.o.";

    public TransferPayment(BigDecimal amountToPay, String methodName, int ordId) {
        super(amountToPay, methodName, ordId);
        transferTitle = getName() + LocalDateTime.now();
    }

    @Override
    public void updateStatus() {
        System.out.println("Twice a day checking bank account history for titles and amount matching data.");
    }


    public String getTitleForTransfer(){
        return transferTitle;
    }


    public String getCompanyAccountNumber() {
        return companyAccountNumber;
    }

    public String getCompanyName() {
        return companyName;
    }
}
