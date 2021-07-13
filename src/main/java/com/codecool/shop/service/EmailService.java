package com.codecool.shop.service;

import com.codecool.shop.model.Order;

public class EmailService implements MessageService{

    @Override
    public void sendMessage(Order order) {
        // payment NOT successful
        if(!order.isPaymentSuccessfull()) {
            // display page with payment refusal and error details, and possibility to return to basket or to main page
            // ??? send en email about refusal of the order - not in specification
            return;
        }

        //payment successful
        // dispaly page with conformation and details of the order, and possibility to get back to main page
        // if ok, save order to json file
        // logic to send email that payment was ok

    }

    @Override
    public void sendConfirmation(String orderNumber, String message) {
        // send an email with conformation
        // email logic
        System.out.println("send email logic");
    }

    @Override
    public void saveAsJSON(Order order) {
        // logic to save order to json file
    }
}
