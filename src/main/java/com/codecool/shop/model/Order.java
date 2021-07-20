package com.codecool.shop.model;


import com.codecool.shop.model.payment.PaymentMethod;

import java.time.LocalDateTime;

public class Order extends BaseModel {

    private boolean isPaymentSuccessfull;
    private String orderNumber = "abcd1234";
    private String customerAddress;
    private String customerName;
    private String email;
    private PaymentMethod payment;
    private Cart customerCart;
    private CustomerData customerData;


    public Order(CustomerData customerData, Cart customerCart) {
        super(customerData.getName(), "transaction date: " + LocalDateTime.now());
        this.customerData = customerData;
        this.customerCart = customerCart;
        this.isPaymentSuccessfull = false; // TODO: remove
    }

    public boolean isPaymentSuccessfull() {
        return isPaymentSuccessfull;
    }

    public void setPaymentSuccessfull(boolean paymentSuccessfull) {
        isPaymentSuccessfull = paymentSuccessfull;
        payment.setFinished(paymentSuccessfull);
    }

    public void setPaymentSuccessfull() {
        isPaymentSuccessfull = true;
    }

    public String getCustomerName() {
        return customerData.getName();
    }

    public String getEmail() {
        return customerData.getCustomerEmail();
    }

    public CustomerData getCustomerData() {
        return customerData;
    }

    public void setCustomerData(CustomerData customerData) {
        this.customerData = customerData;
    }

    public PaymentMethod getPayment() {
        return payment;
    }

    public void setPayment(PaymentMethod payment) {
        this.payment = payment;
    }


    public Cart getCustomerCart() {
        return customerCart;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
