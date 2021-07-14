package com.codecool.shop.model;

import java.time.LocalDateTime;

public class Order extends BaseModel {

    private boolean isPaymentSuccessfull;
    private String orderNumber;
    private String customerAddress;
    private String customerName;

    private String email;

    private Cart customerCart;
    public Order(String orderNumber, String customerAddress, String customerName, String email, Cart customerCart) {
        super(orderNumber + customerName, "transaction date: " + LocalDateTime.now().toString());
        this.orderNumber = orderNumber;
        this.customerAddress = customerAddress;
        this.customerName = customerName;
        this.email = email;
        this.customerCart = customerCart;
        this.isPaymentSuccessfull = false; // TODO: remove
    }

    public boolean isPaymentSuccessfull() {
        return isPaymentSuccessfull;
    }

    public void setPaymentSuccessfull(boolean paymentSuccessfull) {
        isPaymentSuccessfull = paymentSuccessfull;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getEmail() {
        return email;
    }
}
