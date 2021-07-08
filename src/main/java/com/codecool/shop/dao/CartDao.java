package com.codecool.shop.dao;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

import java.math.BigDecimal;
import java.util.HashMap;

public interface CartDao {
//    HashMap<Product, Integer> getAll();
    void add(Cart cart);
    void remove(int id);
    Cart find(int id);
}
