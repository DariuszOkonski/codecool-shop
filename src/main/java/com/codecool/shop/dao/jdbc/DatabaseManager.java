package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.*;
import com.codecool.shop.model.ProductCategory;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DatabaseManager {
    private static CartDao cartDao;
    private static CustomerDataDao customerDataDao;
    private static OrderDao orderDao;
    private static ProductCategoryDao productCategoryDao;
    private static ProductDao productDao;
    private static SupplierDao supplierDao;


    public void setup() throws SQLException {
        DataSource dataSource = connect();
        cartDao = new CartDaoJdbc(dataSource);
        customerDataDao = new CustomerDataDaoJdbc(dataSource);
        orderDao = new OrderDaoJdbc(dataSource);
        productCategoryDao = new ProductCategoryDaoJdbc(dataSource);
        productDao = new ProductDaoJdbc(dataSource);
        supplierDao = new SupplierDaoJdbc(dataSource);

    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = "cc_shop";
        String user = "postgres";
        String password = "zaq12wsx";

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }

}
