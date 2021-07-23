package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {
    private DataSource dataSource;

    public ProductDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Product product) {
        try(Connection conn = dataSource.getConnection()){
            String sql = "INSERT INTO product " +
                    "(name, default_price, default_currency, description, category_id, supplier_id) " +
                    "VALUES (?,?,?,?, " +
                    "(SELECT id FROM product_category WHERE name=?)," +
                    "(SELECT id FROM supplier WHERE name=?))";
            PreparedStatement statement1 = conn.prepareStatement(sql);
            statement1.setString(1, product.getName());
            statement1.setFloat(2, product.getDefaultPrice());
            statement1.setString(3, product.getDefaultCurrency().toString());
            statement1.setString(4, product.getDescription());
            statement1.setString(5, product.getProductCategory().getName());
            statement1.setString(6, product.getSupplier().getName());
            statement1.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
//        try(Connection conn = dataSource.getConnection()){
//            String sqlCategory = "SELECT * FROM product";
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }
}
