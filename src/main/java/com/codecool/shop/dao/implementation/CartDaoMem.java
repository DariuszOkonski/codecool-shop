package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartDaoMem implements CartDao {
    private final List<Cart> data = new ArrayList<>();
    private static CartDaoMem instance = null;

    private CartDaoMem() {
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public Cart getBySessionId(String name) {
        return data.stream()
                .filter(cart -> cart.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void add(Cart cart) {
        int id = data.size() + 1;
        cart.setId(id);
        data.add(cart);
    }

    @Override
    public void addProduct(Cart cart, Product product, int quantity) {
        cart.addProduct(product, quantity);
    }

    @Override
    public void removeProduct(Cart cart, Product product) {
        cart.removeProduct(product);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public Cart find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Cart getNewestOfUser(int id) {
        return data.stream()
                .filter(cart -> cart.getUserId() == id)
                .sorted(Collections.reverseOrder())
                .findFirst()
                .orElse(null);
    }
}
