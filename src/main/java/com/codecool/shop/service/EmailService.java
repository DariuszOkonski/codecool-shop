package com.codecool.shop.service;

import com.codecool.shop.model.Order;

public class EmailService implements MessageService{
    @Override
    public void sendConfirmation(String orderNumber, String message, String email) {
        // email logic
        System.out.println("SENDING EMAIL: ");
        System.out.println("order nr: " + orderNumber);
        System.out.println("message: " + message);
        System.out.println("to: " + email);
    }
}
