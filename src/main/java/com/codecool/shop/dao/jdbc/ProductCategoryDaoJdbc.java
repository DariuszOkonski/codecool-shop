package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class ProductCategoryDaoJdbc implements ProductCategoryDao {
    private DataSource dataSource;

    public ProductCategoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ProductCategory category) {
        try(Connection conn = dataSource.getConnection()){
//
            String sqlCategory = "INSERT INTO product_category (name, department_id, description) " +
                    "VALUES (?, (SELECT id FROM department WHERE name=?), ?)";
            PreparedStatement statement1 = conn.prepareStatement(sqlCategory);
            statement1.setString(1, category.getName());
            statement1.setString(2, category.getDepartment());
            statement1.setString(3, category.getDescription());
            statement1.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ProductCategory find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        return null;
    }
}
