package com.codecool.shop.controller;

import com.codecool.shop.model.Cart;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/registration"})
public class RegistrationController extends BaseController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        setTemplateContext(req, resp);
        serviceSessionValidation(req);

        int userId = (int) req.getSession().getAttribute("user_id");
        String sessionId = req.getSession().getId();

        setContextVariables(cartDataStore.getNewestOfUser(userId), sessionId);

        engine.process("/register.html", context, resp.getWriter());
    }

        @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String user = req.getParameter("username");
            String email = req.getParameter("email");
            String password = req.getParameter("password");



            // TODO check if user exists in db by email
            // ToDO if exist display message


            resp.sendRedirect("/registration");
            // TODO make register service (hash password)
            // TODO add to database
            // TODO alter table user add email field
            // TODO redirect to main page
    }

    private void setContextVariables(Cart cart, String sessionID) {
        // TODO: check if refactorable
        context.setVariable("userId", sessionID);
        context.setVariable("itemsInCart", cart.getTotalProductCount());
        context.setVariable("cart", cart);
    }
}
