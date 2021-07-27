package com.codecool.shop.dao;

import com.codecool.shop.model.CustomerData;

public interface CustomerDataDao {
    CustomerData getByName(String name);

    void add(CustomerData customerData);

    void remove(int id);

    CustomerData find(int id);

}
