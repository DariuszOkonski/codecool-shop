package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartDaoJdbc implements CartDao {
    private DataSource dataSource;

    public CartDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Cart cart) {
        try(Connection conn = dataSource.getConnection()){
            String sql = "";
            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.setString(1, );

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
