package com.codecool.shop.service;

import com.codecool.shop.model.Order;

public class EmailService implements MessageService{

    @Override
    public void sendMessage(Order order, boolean isPaymentSuccessful) {
        // payment NOT successful
        if(!isPaymentSuccessful) {
            // logic to send email that payment was refused
            // display page with payment refusal and possibility to return to basket or to main page
            return;
        }

        //payment successful
        // logic to send email that payment was ok
        // dispaly page with conformation and possibility to get back to main page

    }

    @Override
    public void sendConfirmation(String orderNumber, String message) {

    }
}
