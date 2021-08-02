package com.codecool.shop.controller;

import com.codecool.shop.model.Cart;

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
        int userId = (int) req.getSession().getAttribute("user_id");
        setLoggedUsername(userId);
        String sessionId = req.getSession().getId();

        setContextVariables(cartDataStore.getNewestOfUser(userId), sessionId);

        engine.process("product/cart.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //add to cart
        serviceSessionValidation(req);
        int productId = Integer.parseInt(req.getParameter("product_id"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        int user_id = (int) req.getSession().getAttribute("user_id");

        cartDataStore.addProduct(cartDataStore.getNewestOfUser(user_id), productDataStore.find(productId), quantity );

        //redirect to main page
        String referrer = req.getHeader("referer");
        resp.sendRedirect(referrer);
    }

    private void setContextVariables(Cart cart, String username) {
        // TODO: check if refactorable
        setUserNameToContext();
        context.setVariable("userId", username);
        context.setVariable("itemsInCart", cart.getTotalProductCount());
        context.setVariable("cart", cart);
    }
}
