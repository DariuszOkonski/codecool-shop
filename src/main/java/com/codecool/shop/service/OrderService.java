package com.codecool.shop.service;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.CustomerDataDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.CustomerData;
import com.codecool.shop.model.Order;

public class OrderService {
    private CartDao cartDao;
    private OrderDao orderDao;
    private CustomerDataDao customerDao;

    public OrderService(CartDao cartDao, OrderDao orderDao, CustomerDataDao customerDao) {
        this.cartDao = cartDao;
        this.orderDao = orderDao;
        this.customerDao = customerDao;
    }

    public boolean ordersExists(String sessionId){ // TODO change for userId/orderId
        return orderDao.getByName(sessionId) == null;
    }

    public void setNewOrderForSession(String sessionId){ // TODO change for userId
        CustomerData customerData = customerDao.getByName(sessionId);
        Cart currentCart = cartDao.getBySessionId(sessionId);

        Order order = new Order(customerData, currentCart);
        orderDao.add(order);
    }

    public Order findOrder(String sessionId) { // TODO Find freshest order for userId??
        return orderDao.getByName(sessionId);
    }

}
