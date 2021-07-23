package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;

import javax.sql.DataSource;

public class CartDaoJdbc implements CartDao {
    private DataSource dataSource;

    public CartDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Cart cart) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Cart find(int id) {
        return null;
    }

    @Override
    public Cart getByName(String name) {
        return null;
    }
}
