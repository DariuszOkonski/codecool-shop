package com.codecool.shop.service;

import com.codecool.shop.model.Order;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface MessageService {
    void sendConfirmation(String orderNumber, String message, String email) throws MessagingException;
}
