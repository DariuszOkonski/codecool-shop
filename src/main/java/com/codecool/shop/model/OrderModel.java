package com.codecool.shop.model;

public class OrderModel {
    String sumPrice;

    public OrderModel(Cart cart) {
        sumPrice = cart.getSumPrice().toString();
    }
}
