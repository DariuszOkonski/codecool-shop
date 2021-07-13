package com.codecool.shop.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment extends BaseModel{
    private boolean finished;
    private BigDecimal amountToPay;
    private PaymentMethods method;

    public Payment(BigDecimal amountToPay, String userSessionId, PaymentMethods paymentMethod) {
        super(userSessionId);
        this.amountToPay = amountToPay;
        this.method = paymentMethod;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getTitleForTranser(){
        return getName() + LocalDateTime.now().toString();
    }
}
