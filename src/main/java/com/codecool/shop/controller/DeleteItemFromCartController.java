package com.codecool.shop.controller;

import com.codecool.shop.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(urlPatterns = {"/delete-item"})
public class DeleteItemFromCartController extends BaseController{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setTemplateContext(req, resp);
        serviceSessionValidation(req);

        int productId = Integer.parseInt(req.getParameter("product_id"));


        cartDataStore
                .getByName(req.getSession().getId())
                .removeProduct(productDataStore.find(productId));

//        String referrer =  req.getHeader("referer");
        resp.sendRedirect("/cart");

    }
}
