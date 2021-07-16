package com.codecool.shop.service;

import com.codecool.shop.model.Order;
import com.google.gson.Gson;

public class JSONService implements ReportService {
    @Override
    public String convertData(Order order) {
        Gson json = new Gson();
        String response = json.toJson(order);
        return response;
    }

//    public Order convertData(String jsonFormat) {
//        Gson json = new Gson();
//        Order order = json.fromJson(jsonFormat, Order.class);
//        return order;
//    }
}
