package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
        try(Connection conn = dataSource.getConnection()){
            String sql = "SELECT cat.id, cat.name, dep.name, cat.description FROM product_category as cat " +
                    "INNER JOIN department as dep ON cat.department_id = dep.id " +
                    "WHERE cat.id=(?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                ProductCategory category = new ProductCategory(rs.getString(2), rs.getString(3), rs.getString(4));
                category.setId(rs.getInt(1));
                return category;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> categories = new ArrayList<>();
        try(Connection conn = dataSource.getConnection()){
            String sql = "SELECT cat.id, cat.name, dep.name, cat.description FROM product_category as cat INNER JOIN department as dep ON cat.department_id = dep.id";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                ProductCategory category = new ProductCategory(rs.getString(2), rs.getString(3), rs.getString(4));
                category.setId(rs.getInt(1));
                categories.add(category);
            }
            return categories;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
