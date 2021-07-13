package com.codecool.shop.service;

import com.codecool.shop.model.Order;

public interface MessageService {
    void sendConfirmation(String orderNumber, String message, String email);
}
