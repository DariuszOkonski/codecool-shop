package com.codecool.shop.model.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditCard {
    private String cardNum;
    private String yearExpirationNum;
    private String monthExpirationNum;
    private String cvv;
    private BigDecimal funds = BigDecimal.valueOf(500);

    public CreditCard(String cardNum, String yearExpirationNum, String monthExpirationNum, String cvv) {
        this.cardNum = cardNum;
        this.yearExpirationNum = yearExpirationNum;
        this.monthExpirationNum = monthExpirationNum;
        this.cvv = cvv;
    }
    public boolean isPaymentPossible(BigDecimal sumToPay){
        return isDataCorrect() && fundsEnoughFor(sumToPay);
    }

    public boolean isDataCorrect(){
        return cardNumCorrect() && isNotExpired() && serialNumCorrect();
    }

    public boolean fundsEnoughFor(BigDecimal amount){
        return funds.compareTo(amount) > 0;
    }


    private boolean serialNumCorrect() {
        return stringNumIsNumeric(cvv) && cvv.length() == 3;
    }

    private boolean cardNumCorrect(){
        return stringNumIsNumeric(cardNum) && cardNum.length() == 16;
    }

    private boolean stringNumIsNumeric(String stringNumb) {
        for (int i=0; i< stringNumb.length(); i++) {
            if (!charIsNumeric(String.valueOf(stringNumb.charAt(i)))){
                return false;
            }
        }
        return true;
    }
    private boolean charIsNumeric(String chr) {
        try {
            return (Integer.parseInt(chr) <= 9) && (Integer.parseInt(chr) >= 0);
        } catch (NumberFormatException er){
            return false;
        }
    }

    private boolean isNotExpired(){
        return LocalDate.now().isBefore(LocalDate.parse(yearExpirationNum + "-" + monthExpirationNum + "-" + "01"));
    }

    public void decreaseFunds(BigDecimal sumPrice) {
        funds = funds.subtract(sumPrice);
    }
}
