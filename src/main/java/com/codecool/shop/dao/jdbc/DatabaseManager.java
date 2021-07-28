package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.service.ConfigService;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class DatabaseManager {
    public static DatabaseManager INSTANCE;


    private CartDao cartDao;
    private CustomerDataDao customerDataDao;
    private OrderDao orderDao;
    private ProductCategoryDao productCategoryDao;
    private ProductDao productDao;
    private SupplierDao supplierDao;
    private UserDao userDao;


    public void setup() throws SQLException, IOException {
        DataSource dataSource = connect();
        cartDao = new CartDaoJdbc(dataSource);
        customerDataDao = new CustomerDataDaoJdbc(dataSource);
        orderDao = new OrderDaoJdbc(dataSource);
        productCategoryDao = new ProductCategoryDaoJdbc(dataSource);
        productDao = new ProductDaoJdbc(dataSource);
        supplierDao = new SupplierDaoJdbc(dataSource);
        userDao = new UserDaoJdbc(dataSource);

    }

    private DataSource connect() throws SQLException, IOException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        ConfigService configService = new ConfigService();

        String dbName = configService.getDatabaseName();
        String user = configService.getDatabaseUser();
        String password = configService.getDatabasePassword();

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }


    public static DatabaseManager getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseManager();
            try {
                INSTANCE.setup();
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        }
        return INSTANCE;
    }

    public CartDao getCartDao() {
        return cartDao;
    }

    public CustomerDataDao getCustomerDataDao() {
        return customerDataDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public ProductCategoryDao getProductCategoryDao() {
        return productCategoryDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public SupplierDao getSupplierDao() {
        return supplierDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
