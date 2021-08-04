package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.payment.PaymentMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderDaoMem implements OrderDao {
    private List<Order> data = new ArrayList<>();

    @Override
    public Order getNewestOfUser(int userId) {
        return data.stream()
                .filter(order -> order.getUserId() == userId)
                .sorted(Collections.reverseOrder())
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Order> getAllOrdersForSpecificUser(int user_id) {
        return null;
    }

    @Override
    public void setPaymentStatus(Order ord, boolean paymentPossible) {
        ord.setPaymentSuccessfull(paymentPossible);
    }

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
    public void setPaymentMethod(Order ord, String method) {
        ord.setPayment(
                PaymentMethods.build(method, ord.getCustomerCart().getSumPrice(), ord.getId())
        );
    }

    @Override
    public Order find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Order getByName(String name) {
        return data.stream()
                .filter(order -> order.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
