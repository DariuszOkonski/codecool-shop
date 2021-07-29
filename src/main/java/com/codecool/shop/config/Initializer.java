package com.codecool.shop.config;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.dao.jdbc.DatabaseManager;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.service.ConfigService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

@WebListener
public class Initializer implements ServletContextListener {
    private ProductDao productDataStore;
    private ProductCategoryDao productCategoryDataStore;
    private SupplierDao supplierDataStore;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ConfigService configService = new ConfigService();

        try {
            if (configService.getDaoType().equals("jdbc")) {
                productDataStore = DatabaseManager.getINSTANCE().getProductDao();
                productCategoryDataStore = DatabaseManager.getINSTANCE().getProductCategoryDao();
                supplierDataStore = DatabaseManager.getINSTANCE().getSupplierDao();
            } else {
                productDataStore = ProductDaoMem.getInstance();
                productCategoryDataStore = ProductCategoryDaoMem.getInstance();
                supplierDataStore = SupplierDaoMem.getInstance();
            }
        } catch (IOException e) {
            //TODO LOG EXCEPTION
            System.out.println(e);
        }





        //setting up a new supplier
        if (productCategoryDataStore.getAll().size() == 0 && productDataStore.getAll().size() == 0 && supplierDataStore.getAll().size() == 0){
            Supplier amazon = new Supplier("Amazon", "Digital content and services");
            supplierDataStore.add(amazon);
            Supplier lenovo = new Supplier("Lenovo", "Computers");
            supplierDataStore.add(lenovo);

            Supplier huawei = new Supplier("Huawei", "Computers");
            supplierDataStore.add(huawei);

            Supplier acer = new Supplier("acer", "Computers");
            supplierDataStore.add(acer);


        //setting up a new product category


            ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
            ProductCategory laptops = new ProductCategory("Laptops", "Hardware", "A laptop computer, commonly shortened to notebook, it is handy and mobile.");
            productCategoryDataStore.add(tablet);
            productCategoryDataStore.add(laptops);

        //setting up products and printing it
            productDataStore.add(new Product
                    ("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
            productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
            productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));

            productDataStore.add(new Product("Huawei MateBook D 15 i5", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", laptops, huawei));
            productDataStore.add(new Product("Acer Aspire 3 i5-1135G7", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", laptops, acer));
            productDataStore.add(new Product("Huawei MateBook 13", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", laptops, huawei));

//            cartDataStore.add(new Cart("temporary"));
        }
        System.out.println("http://localhost:8080");
    }


}

