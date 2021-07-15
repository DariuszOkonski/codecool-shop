package com.codecool.shop.service;

import javax.mail.MessagingException;

public interface MessageService {
    void sendConfirmation(String orderNumber, String message, String email) throws MessagingException;
}
