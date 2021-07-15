package com.codecool.shop.dao;

import com.codecool.shop.model.Order;

public interface OrderDao {
    void add(Order order);
    void remove(int id);
    Order find(int id);
    Order getByName(String userName);
}
