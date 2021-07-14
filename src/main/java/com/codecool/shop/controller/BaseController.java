package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
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
    protected ProductService productService = new ProductService(productDataStore,productCategoryDataStore, supplierDataStore);
    protected CustomerDataDao customerDataStore = CustomerDataDaoMem.getInstance();
    protected TemplateEngine engine = null;
    protected WebContext context = null;

    private boolean doesCartSessionExist(HttpServletRequest req){
        HttpSession session = req.getSession();
        boolean isUserInSession = session.getAttribute("user_id") != null;
        return isUserInSession;
    }

    private void setSessionCart(HttpServletRequest req) {
        String sessionId = req.getSession().getId();
        cartDataStore.add(new Cart(sessionId));
    }

    protected void serviceSessionValidation(HttpServletRequest req) {
        if (!doesCartSessionExist(req)){
            System.out.println("Session setting");
            setSessionCart(req);
        }
        System.out.println(req.getSession().getId());
    }

    protected void setTemplateContext(HttpServletRequest req, HttpServletResponse resp) {
        engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        context = new WebContext(req, resp, req.getServletContext());
    }
}
