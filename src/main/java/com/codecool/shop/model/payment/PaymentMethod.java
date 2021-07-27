package com.codecool.shop.model.payment;

import java.math.BigDecimal;

public interface PaymentMethod {
    void updateStatus();
    boolean isFinished();
    void setFinished(boolean finished);

    String getName();
}
