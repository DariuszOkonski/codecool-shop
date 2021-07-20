package com.codecool.shop.service;

import com.codecool.shop.model.Order;
import com.google.gson.Gson;

public class JSONService implements ReportService {
    @Override
    public String convertData(Order order) {
        Gson json = new Gson();
        return json.toJson(order);
    }

}
