package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        ProductService productService = new ProductService(productDataStore,productCategoryDataStore);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());


        List<ProductCategory> productCategoryList = productCategoryDataStore.getAll();

//        for (var productCategory: productCategoryList) {
//            var categoryProducts = productService.getProductsForCategory(productCategory.getId());
//            productCategory.setProducts((ArrayList<Product>) categoryProducts);
//        }

        int category_id = 1; //default value

        if (req.getParameter("category_id") != null) category_id = Integer.parseInt(req.getParameter("category_id"));

        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("category", productService.getProductCategory(category_id));

        context.setVariable("products", productService.getProductsForCategory(category_id));



//        System.out.println(productCategoryDataStore.getAll());
//        context.setVariable("category2", productService.getProductCategory(2));
//        context.setVariable("products2", productService.getProductsForCategory(2));
//        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        engine.process("product/index.html", context, resp.getWriter());
    }

}
