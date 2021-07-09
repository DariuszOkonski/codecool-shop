package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {
//    private int sessionsInside = 1;
    private ProductDao productDataStore = ProductDaoMem.getInstance();
    private ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
    private SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
    private CartDao cartDataStore = CartDaoMem.getInstance();
    private ProductService productService = new ProductService(productDataStore,productCategoryDataStore, supplierDataStore);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        serviceSessionValidation(req);

        int category_id = 1; //default value
        int supplier_id = 0; //default value
        List<Product> products = new ArrayList<>();
        List<Supplier> suppliers = supplierDataStore.getAll();

        boolean categoryProvided = req.getParameter("category_id") != null;
        boolean categoryAndSupplierProvided = categoryProvided && Integer.parseInt(req.getParameter("supplier_id")) != 0;
        boolean supplierProvided = req.getParameter("supplier_id") != null && Integer.parseInt(req.getParameter("supplier_id")) != 0;

        if (categoryAndSupplierProvided) {

           products = filterByCategoryAndSupplier(req);

        }else if (categoryProvided) {
            category_id = Integer.parseInt(req.getParameter("category_id"));
            products = productService.getProductsForCategory(category_id);
        }else if (supplierProvided) {
            supplier_id = Integer.parseInt(req.getParameter("supplier_id"));
            products = productService.getProductsForSupplier(supplier_id);
        } else {
            products = productService.getProductsForCategory(category_id);
        }

        setContextVariables(productCategoryDataStore, productService, context, category_id, products, suppliers);

        engine.process("product/index.html", context, resp.getWriter());
    }

    private List<Product> filterByCategoryAndSupplier(HttpServletRequest req) {
        int category_id = Integer.parseInt(req.getParameter("category_id"));
        int supplier_id = Integer.parseInt(req.getParameter("supplier_id"));
        List<Product> products = productService.getProductsForCategory(category_id);
        int finalSupplier_id = supplier_id;
        return products.stream()
                .filter(product -> product.getSupplier().getId() == finalSupplier_id)
                .collect(Collectors.toList());
    }

    private void setContextVariables(ProductCategoryDao productCategoryDataStore, ProductService productService, WebContext context, int category_id, List<Product> products, List<Supplier> suppliers) {
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("category", productService.getProductCategory(category_id));
        context.setVariable("suppliers", suppliers);
        context.setVariable("products", products);
    }

    private boolean doesCartSessionExist(HttpServletRequest req){
        HttpSession session = req.getSession();
        boolean isUserInSession = session.getAttribute("user_id") != null;
        return isUserInSession;
    }

    private void setSessionCart(HttpServletRequest req) {
        String sessionId = req.getSession().getId();
        cartDataStore.add(new Cart(sessionId));
    }

    private void serviceSessionValidation(HttpServletRequest req) {
        if (!doesCartSessionExist(req)){
            System.out.println("Session setting");
            setSessionCart(req);
        }
        System.out.println(req.getSession().getId());
    }

}

