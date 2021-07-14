package com.codecool.shop.model.payment;

import com.codecool.shop.model.BaseModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Payment extends BaseModel implements PaymentMethod{
    private boolean finished;
    private BigDecimal amountToPay;

    public Payment(BigDecimal amountToPay, String userSessionId) {
        super(userSessionId);
        this.amountToPay = amountToPay;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

}
