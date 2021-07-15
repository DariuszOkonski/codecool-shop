package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;

import java.util.ArrayList;
import java.util.List;

public class OrderDaoMem implements OrderDao {
    private List<Order> data = new ArrayList<>();
    private static OrderDaoMem instance = null;

    private OrderDaoMem() {
    }


    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Order cart) {
        int id = data.size() + 1;
        cart.setId(id);
        data.add(cart);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }
    @Override
    public Order find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Order getByName(String name) {
        return data.stream()
                .filter(order -> order.getName() == name)
                .findFirst()
                .orElse(null);
    }
}
