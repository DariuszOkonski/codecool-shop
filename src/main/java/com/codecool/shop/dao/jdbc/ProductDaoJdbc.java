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
import java.util.ArrayList;
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
        try(Connection conn = dataSource.getConnection()){
            String sql = "SELECT " +
                    " prd.name," +
                    " prd.description," +
                    " prd.default_price," +
                    " prd.default_currency," +
                    " cat.name," +
                    " cat.description, " +
                    "dep.name, " +
                    "sup.name, " +
                    "sup.description, " +
                    "prd.id, " +
                    "cat.id, " +
                    "sup.id " +
                    "  FROM product as prd " +
                    "INNER JOIN product_category AS cat ON prd.category_id = cat.id " +
                    "INNER JOIN supplier AS sup ON prd.supplier_id = sup.id "+
                    "INNER JOIN department AS dep ON cat.department_id = dep.id " +
                    "WHERE prd.id = (?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                ProductCategory category = new ProductCategory(rs.getString(5), rs.getString(7), rs.getString(6));
                category.setId(rs.getInt(11));
                Supplier supplier = new Supplier(rs.getString(8), rs.getString(9));
                supplier.setId(rs.getInt(12));
                Product product = new Product(rs.getString(1), rs.getFloat(3), rs.getString(4), rs.getString(2), category, supplier);
                product.setId(rs.getInt(10));

                System.out.println(product);
                return product;
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
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try(Connection conn = dataSource.getConnection()){
            String sql = "SELECT " +
                    " prd.name," +
                    " prd.description," +
                    " prd.default_price," +
                    " prd.default_currency," +
                    " cat.name," +
                    " cat.description, " +
                    "dep.name, " +
                    "sup.name, " +
                    "sup.description, " +
                    "prd.id, " +
                    "cat.id, " +
                    "sup.id " +
                    "  FROM product as prd " +
                    "INNER JOIN product_category AS cat ON prd.category_id = cat.id " +
                    "INNER JOIN supplier AS sup ON prd.supplier_id = sup.id "+
                    "INNER JOIN department AS dep ON cat.department_id = dep.id";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                ProductCategory category = new ProductCategory(rs.getString(5), rs.getString(7), rs.getString(6));
                category.setId(rs.getInt(11));
                Supplier supplier = new Supplier(rs.getString(8), rs.getString(9));
                supplier.setId(rs.getInt(12));
                Product product = new Product(rs.getString(1), rs.getFloat(3), rs.getString(4), rs.getString(2), category, supplier);
                product.setId(rs.getInt(10));
                products.add(product);
            }
            System.out.println(products);
            return products;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        List<Product> products = new ArrayList<>();
        try(Connection conn = dataSource.getConnection()){
            String sql = "SELECT " +
                    " prd.name," +
                    " prd.description," +
                    " prd.default_price," +
                    " prd.default_currency," +
                    " cat.name," +
                    " cat.description, " +
                    "dep.name, " +
                    "sup.name, " +
                    "sup.description, " +
                    "prd.id, " +
                    "cat.id, " +
                    "sup.id " +
                    "  FROM product as prd " +
                    "INNER JOIN product_category AS cat ON prd.category_id = cat.id " +
                    "INNER JOIN supplier AS sup ON prd.supplier_id = sup.id "+
                    "INNER JOIN department AS dep ON cat.department_id = dep.id " +
                    "WHERE prd.supplier_id = (?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, supplier.getId());
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                ProductCategory category = new ProductCategory(rs.getString(5), rs.getString(7), rs.getString(6));
                category.setId(rs.getInt(11));
                Supplier productSupplier = new Supplier(rs.getString(8), rs.getString(9));
                productSupplier.setId(rs.getInt(12));
                Product product = new Product(rs.getString(1), rs.getFloat(3), rs.getString(4), rs.getString(2), category, productSupplier);
                product.setId(rs.getInt(10));
                products.add(product);
            }
            System.out.println(products);
            return products;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        List<Product> products = new ArrayList<>();
        try(Connection conn = dataSource.getConnection()){
            String sql = "SELECT " +
                    " prd.name," +
                    " prd.description," +
                    " prd.default_price," +
                    " prd.default_currency," +
                    " cat.name," +
                    " cat.description, " +
                    "dep.name, " +
                    "sup.name, " +
                    "sup.description, " +
                    "prd.id, " +
                    "cat.id, " +
                    "sup.id " +
                    "  FROM product as prd " +
                    "INNER JOIN product_category AS cat ON prd.category_id = cat.id " +
                    "INNER JOIN supplier AS sup ON prd.supplier_id = sup.id "+
                    "INNER JOIN department AS dep ON cat.department_id = dep.id " +
                    "WHERE prd.category_id = (?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, productCategory.getId());
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                ProductCategory category = new ProductCategory(rs.getString(5), rs.getString(7), rs.getString(6));
                category.setId(rs.getInt(11));
                Supplier supplier = new Supplier(rs.getString(8), rs.getString(9));
                supplier.setId(rs.getInt(12));
                Product product = new Product(rs.getString(1), rs.getFloat(3), rs.getString(4), rs.getString(2), category, supplier);
                product.setId(rs.getInt(10));
                products.add(product);
            }
            System.out.println(products);
            return products;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
