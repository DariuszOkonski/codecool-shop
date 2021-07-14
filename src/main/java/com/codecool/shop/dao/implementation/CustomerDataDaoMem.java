package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CustomerDataDao;
import com.codecool.shop.model.CustomerData;

import java.util.ArrayList;
import java.util.List;

public class CustomerDataDaoMem implements CustomerDataDao {
    private List<CustomerData> data = new ArrayList<>();
    private static CustomerDataDao instance = null;

    private CustomerDataDaoMem() {
    }

    public static CustomerDataDao getInstance() {
        if(instance == null) {
            instance = new CustomerDataDaoMem();
        }
        return instance;
    }

    @Override
    public CustomerData getByName(String name) {
        return data.stream()
                .filter(cart -> cart.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void add(CustomerData customerData) {
        int id = data.size() + 1;
        customerData.setId(id);
        data.add(customerData);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public CustomerData find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }
}
