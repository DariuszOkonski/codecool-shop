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

    public boolean hasNotPendingOrder(int userId){ // TODO change for userId/orderId
        try {
            Order newestOrder = orderDao.getNewestOfUser(userId);
            return newestOrder.getPayment() != null && newestOrder.isPaymentSuccessfull() != false;
        } catch (NullPointerException e){
            return true;
        }
    }

    public void setFreshOrderForUser(int userId){ // TODO change for userId
        CustomerData customerData = customerDao.find(userId);
        Cart currentCart = cartDao.getNewestOfUser(userId);

        Order order = new Order(customerData, currentCart);
        orderDao.add(order);
    }

    public Order getNewestOrder(int userId) { // TODO Find freshest order for userId??
        return orderDao.getNewestOfUser(userId);
    }

}
