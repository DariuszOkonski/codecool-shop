package com.codecool.shop.model.payment;

import com.codecool.shop.model.BaseModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Payment extends BaseModel implements PaymentMethod{
    private boolean finished;
    private BigDecimal amountToPay;

    public BigDecimal getAmountToPay() {
        return amountToPay;
    }

    public int getOrderId() {
        return orderId;
    }

    private int orderId;

    public Payment(BigDecimal amountToPay, String methodName, int ordId) {
        super(methodName);
        this.orderId = ordId;
        this.amountToPay = amountToPay;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

}
