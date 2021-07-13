package com.codecool.shop.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Order extends BaseModel {

    private boolean isPaymentSuccessful;
    private String orderNumber;
    private String customerAddress;
    private String customerName;
    private Cart customerCart;

    public Order(String orderNumber, String customerAddress, String customerName, Cart customerCart) {
        super(orderNumber + customerName, "transaction date: " + LocalDateTime.now().toString());
        this.orderNumber = orderNumber;
        this.customerAddress = customerAddress;
        this.customerName = customerName;
        this.customerCart = customerCart;
        this.isPaymentSuccessful = true; // TODO: remove
    }

    public boolean isPaymentSuccessful() {
        return isPaymentSuccessful;
    }

    public void setPaymentSuccessful(boolean paymentSuccessful) {
        isPaymentSuccessful = paymentSuccessful;
    }
}
