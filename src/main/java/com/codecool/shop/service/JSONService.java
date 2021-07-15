package com.codecool.shop.service;

public class JSONService implements ReportService {
    @Override
    public void saveData(Order order) {
        // logic to save order to json file
        System.out.println("SAVING DATA TO JSON");
        System.out.println("order nr: " + order.getOrderNumber());
    }
}
