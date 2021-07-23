package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDaoJdbc implements OrderDao {
    private DataSource dataSource;

    public OrderDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Order order) {
        try(Connection conn = dataSource.getConnection()){
            String sql = "";
            PreparedStatement statement = conn.prepareStatement(sql);
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
    public Order find(int id) {
        return null;
    }

    @Override
    public Order getByName(String userName) {
        return null;
    }
}
