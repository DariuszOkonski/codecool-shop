package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.CustomerDataDao;
import com.codecool.shop.model.CustomerData;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDataDaoJdbc implements CustomerDataDao {
    private DataSource dataSource;

    public CustomerDataDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public CustomerData getByName(String name) {
        return null;
    }

    @Override
    public void add(CustomerData customerData) {
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
    public CustomerData find(int id) {
        return null;
    }
}
