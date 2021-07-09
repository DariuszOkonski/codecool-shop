package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.service.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/add_to_cart"})
public class CartController extends BaseController {

//    private ProductDao productDataStore = ProductDaoMem.getInstance();
//    private ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
//    private SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
//    private CartDao cartDataStore = CartDaoMem.getInstance();
//    private ProductService productService = new ProductService(productDataStore,productCategoryDataStore, supplierDataStore);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setTemplateContext(req, resp);
        serviceSessionValidation(req);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //add to cart

        int productId = Integer.parseInt(req.getParameter("product_id"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        System.out.println(productId + quantity);

        cartDataStore.getByName(req.getSession().getId()).addProduct(productDataStore.find(productId), quantity);

        System.out.println(cartDataStore.getByName(req.getSession().getId()).toString());


        //redirect to main page
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
