package com.codecool.shop.dao;

import com.codecool.shop.model.Order;

import java.util.List;

public interface OrderDao {
    int FINISHED_ID = 2;
    int NOT_FINISHED_ID = 1;
    String USER_ID = "user_id";
    String CART_ID = "cart_id";
    String ID = "id";
    String PAYMENT_METHOD = "payment_method";
    String ORDER_STATUS_ID = "order_status_id";
    void add(Order order);
    void remove(int id);
    void setPaymentMethod(Order ord, String method);
    Order find(int id);
    Order getByName(String userName);
    Order getNewestOfUser(int userId);
    List<Order> getAllOrdersForSpecificUser(int user_id);
    void setPaymentStatus(Order ord, boolean paymentPossible);
}
