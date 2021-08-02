package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.service.ProductService;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends BaseController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        setTemplateContext(req, resp);
        serviceSessionValidation(req);

        List<Product> products = productDataStore.getAll();
        List<Supplier> suppliers = supplierDataStore.getAll();

        int supplier_id = req.getParameter("supplier_id") == null ? 0 : Integer.parseInt(req.getParameter("supplier_id"));
        int category_id = req.getParameter("category_id") == null ? 0 : Integer.parseInt(req.getParameter("category_id"));
        int userId = (int) req.getSession().getAttribute("user_id");

        setLoggedUsername(userId);

        boolean categoryProvided = category_id != 0;
        boolean supplierProvided = supplier_id != 0;
        boolean categoryAndSupplierProvided = categoryProvided && supplierProvided;

        if (categoryAndSupplierProvided) {
           products = productService.getProductsForCategoryOfSupplier(category_id, supplier_id);
        }else if (categoryProvided) {
            products = productService.getProductsForCategory(category_id);
        }else if (supplierProvided) {
            products = productService.getProductsForSupplier(supplier_id);
        }
        Cart cart = cartDataStore.getNewestOfUser((int)req.getSession().getAttribute("user_id"));
        int totalInCart = cart.getTotalProductCount();

        setContextVariables(productCategoryDataStore, productService, context, category_id, products, suppliers, req.getSession().getId(), totalInCart, supplier_id);

        engine.process("product/index.html", context, resp.getWriter());
    }


    private void setContextVariables(ProductCategoryDao productCategoryDataStore, ProductService productService, WebContext context, int category_id, List<Product> products, List<Supplier> suppliers, String sessionID, int itemsInCart, int supplierId) {
        // TODO: check if refactorable

        setUserNameToContext();
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("category", productService.getProductCategory(category_id));
        context.setVariable("supplier", supplierDataStore.find(supplierId));
        context.setVariable("suppliers", suppliers);
        context.setVariable("products", products);
        context.setVariable("userId", sessionID);
        context.setVariable("itemsInCart", itemsInCart);
    }

}

