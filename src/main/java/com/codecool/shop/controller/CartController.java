package com.codecool.shop.controller;

import com.codecool.shop.model.Cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends BaseController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setTemplateContext(req, resp);
        serviceSessionValidation(req);
        String sessionId = req.getSession().getId();

        setContextVariables(cartDataStore.getByName(sessionId), sessionId);

        engine.process("product/cart.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //add to cart
        serviceSessionValidation(req);
        int productId = Integer.parseInt(req.getParameter("product_id"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        System.out.println(productId + quantity);

        cartDataStore.getByName(req.getSession().getId()).addProduct(productDataStore.find(productId), quantity);

        System.out.println(cartDataStore.getByName(req.getSession().getId()).toString());


        //redirect to main page
        String referrer = req.getHeader("referer");
        resp.sendRedirect(referrer);
    }

    private void setContextVariables(Cart cart, String sessionID) {
        // TODO: check if refactorable
        context.setVariable("userId", sessionID);
        context.setVariable("itemsInCart", cart.getTotalProductCount());
        context.setVariable("cart", cart);
    }
}
