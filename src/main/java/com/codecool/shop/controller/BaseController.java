package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.dao.jdbc.DatabaseManager;
import com.codecool.shop.model.Cart;
import com.codecool.shop.service.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class BaseController extends HttpServlet {
    protected ProductDao productDataStore = ProductDaoMem.getInstance();
    protected ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
    protected SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
    protected CartDao cartDataStore = CartDaoMem.getInstance();
    protected OrderDao orderDataStore = OrderDaoMem.getInstance();
    protected UserDao userDataStore = UserDaoMem.getInstance();

    protected ProductService productService = null;
    protected CustomerDataDao customerDataStore = null;
    protected TemplateEngine engine = null;
    protected WebContext context = null;

    public BaseController() {

        productDataStore = DatabaseManager.getINSTANCE().getProductDao();
        productCategoryDataStore = DatabaseManager.getINSTANCE().getProductCategoryDao();
        supplierDataStore = DatabaseManager.getINSTANCE().getSupplierDao();
        cartDataStore = DatabaseManager.getINSTANCE().getCartDao();
        orderDataStore = DatabaseManager.getINSTANCE().getOrderDao();
        customerDataStore = DatabaseManager.getINSTANCE().getCustomerDataDao();
        userDataStore = DatabaseManager.getINSTANCE().getUserDao();


        productService = new ProductService(productDataStore,productCategoryDataStore, supplierDataStore);
        engine = null;
        context = null;

    }

    private boolean doesCartSessionExist(HttpServletRequest req){
        HttpSession session = req.getSession();
        return session.getAttribute("user_id") != null;
    }

    void setNewCart(HttpServletRequest req) {
        String sessionId = req.getSession().getId();
        int newId = userDataStore.createNewGuest();
        req.getSession().setAttribute("user_id", newId);
        Cart cart = new Cart(sessionId);
        cart.setUserId(newId);

        cartDataStore.add(cart);
    }

    protected void serviceSessionValidation(HttpServletRequest req) {
        if (!doesCartSessionExist(req)){
            System.out.println("Session setting");
            setNewCart(req);
        }
        System.out.println("session already set");
    }

    protected void setTemplateContext(HttpServletRequest req, HttpServletResponse resp) {
        engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        context = new WebContext(req, resp, req.getServletContext());
    }
}
