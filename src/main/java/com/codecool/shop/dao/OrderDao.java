package com.codecool.shop.dao;

public interface OrderDao {
    void add(Order order);
    void remove(int id);
    Order find(int id);
    Order getByName(String userName);
}
