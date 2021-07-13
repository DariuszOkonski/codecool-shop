package com.codecool.shop.controller;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.PaymentMethods;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends BaseController{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setTemplateContext(req, resp);
        serviceSessionValidation(req);

        String currentSession = req.getSession().getId();
        BigDecimal amountToPay = cartDataStore.getByName(currentSession).getSumPrice();
        setContextVariables(currentSession, amountToPay);

        engine.process("product/payment.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //add to cart


        //redirect to main page
        String referrer = ((HttpServletRequest) req).getHeader("referer");
        ((HttpServletResponse) resp).sendRedirect(referrer);
    }

    private void setContextVariables(String sessionID, BigDecimal cartSumPrice) {
        // TODO: check if refactorable
        context.setVariable("userId", sessionID);
        context.setVariable("amountToPay", cartSumPrice);
        context.setVariable("paymentMethods", PaymentMethods.values());
    }
}
