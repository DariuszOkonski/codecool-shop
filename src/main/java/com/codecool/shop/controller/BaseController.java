package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.dao.jdbc.DatabaseManager;
import com.codecool.shop.model.Cart;
import com.codecool.shop.service.CartService;
import com.codecool.shop.service.ConfigService;
import com.codecool.shop.service.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
    protected CartService cartService = null;
    protected ConfigService configService = new ConfigService();

    protected String loggedUsername;

    public BaseController() {

        try {
            if (configService.getDaoType().equals("jdbc")) {
                setDatabaseDao();
            } else {
                setMemoryDao();
            }
        } catch (IOException e) {
            //TODO LOG EXCEPTION
            System.out.println(e);
        }


        productService = new ProductService(productDataStore,productCategoryDataStore, supplierDataStore);
        engine = null;
        context = null;
        cartService = new CartService(cartDataStore, productDataStore);

    }

    private void setDatabaseDao() {
        productDataStore = DatabaseManager.getINSTANCE().getProductDao();
        productCategoryDataStore = DatabaseManager.getINSTANCE().getProductCategoryDao();
        supplierDataStore = DatabaseManager.getINSTANCE().getSupplierDao();
        cartDataStore = DatabaseManager.getINSTANCE().getCartDao();
        orderDataStore = DatabaseManager.getINSTANCE().getOrderDao();
        customerDataStore = DatabaseManager.getINSTANCE().getCustomerDataDao();
        userDataStore = DatabaseManager.getINSTANCE().getUserDao();
    }

    private void setMemoryDao() {
        productDataStore = ProductDaoMem.getInstance();
        productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        supplierDataStore = SupplierDaoMem.getInstance();
        cartDataStore = CartDaoMem.getInstance();
        orderDataStore = OrderDaoMem.getInstance();
        userDataStore = UserDaoMem.getInstance();
    }

    private boolean doesCartSessionExist(HttpServletRequest req){
        HttpSession session = req.getSession();
        return session.getAttribute("user_id") != null;
    }

    // TODO: bug is here, to fix not to set new quest after successful payment
    void createNewGuest(HttpServletRequest req) {
        int newId = userDataStore.createNewGuest();
        req.getSession().setAttribute("user_id", newId);
    }

    void setNewCart(HttpServletRequest req) {
        String sessionId = req.getSession().getId();
        int newId = (int)req.getSession().getAttribute("user_id");
//        req.getSession().setAttribute("user_id", newId);
        Cart cart = new Cart(sessionId);
        cart.setUserId(newId);

        cartDataStore.add(cart);
    }

    void setNewCart(int userId) {

//        req.getSession().setAttribute("user_id", newId);
        Cart cart = new Cart(userId);
        cart.setUserId(userId);

        cartDataStore.add(cart);
    }

    protected void serviceSessionValidation(HttpServletRequest req) {
        if (!doesCartSessionExist(req)){
            System.out.println("Session setting");
            createNewGuest(req);
            setNewCart(req);
        }
        System.out.println("session already set");
    }

    protected void setTemplateContext(HttpServletRequest req, HttpServletResponse resp) {
        engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        context = new WebContext(req, resp, req.getServletContext());
    }

    protected void setUserNameToContext(){
        context.setVariable("username", loggedUsername);
    }

    protected void setLoggedUsername(int userId){
        loggedUsername = userDataStore.getById(userId).getName();
    }
}
