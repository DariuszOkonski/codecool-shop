package com.codecool.shop.service;

import com.codecool.shop.model.Order;

public interface MessageService {
    void sendMessage(Order order);
    void sendConfirmation(String orderNumber, String message);
    void saveAsJSON(Order order);
}
