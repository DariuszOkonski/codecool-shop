package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        ProductService productService = new ProductService(productDataStore,productCategoryDataStore, supplierDataStore);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        // TODO 1. category - list<products> 2. supplier(list<products>) - ...

        int category_id = 1; //default value
        int supplier_id = 0; //default value
        List<Product> products = new ArrayList<>();
        List<Supplier> suppliers = supplierDataStore.getAll();


        if (req.getParameter("category_id") != null && Integer.parseInt(req.getParameter("supplier_id")) != 0) {
            category_id = Integer.parseInt(req.getParameter("category_id"));
            supplier_id = Integer.parseInt(req.getParameter("supplier_id"));
            products = productService.getProductsForCategory(category_id);
            int finalSupplier_id = supplier_id;
            products = products.stream()
                    .filter(product -> product.getSupplier().getId() == finalSupplier_id)
                    .collect(Collectors.toList());
        }else if (req.getParameter("category_id") != null) {
            category_id = Integer.parseInt(req.getParameter("category_id"));
            products = productService.getProductsForCategory(category_id);
        }else if (req.getParameter("supplier_id") != null && Integer.parseInt(req.getParameter("supplier_id")) != 0) {
            supplier_id = Integer.parseInt(req.getParameter("supplier_id"));
            products = productService.getProductsForSupplier(supplier_id);
        } else {
            products = productService.getProductsForCategory(category_id);
        }
        System.out.println(productService.getProductsForSupplier(supplier_id));

        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("category", productService.getProductCategory(category_id));
        context.setVariable("suppliers", suppliers);

        context.setVariable("products", products);


//        for (var productCategory: productCategoryList) {
//            var categoryProducts = productService.getProductsForCategory(productCategory.getId());
//            productCategory.setProducts((ArrayList<Product>) categoryProducts);
//        }

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
