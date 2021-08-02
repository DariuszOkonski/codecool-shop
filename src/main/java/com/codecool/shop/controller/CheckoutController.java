package com.codecool.shop.controller;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.CustomerData;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends BaseController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setTemplateContext(req, resp);
        serviceSessionValidation(req);
        String sessionId = req.getSession().getId();
        int userId = (int) req.getSession().getAttribute("user_id");
        setLoggedUsername(userId);

        setContextVariables(cartDataStore.getNewestOfUser(userId), sessionId);

        engine.process("product/checkout.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setTemplateContext(req, resp);
        serviceSessionValidation(req);
        String sessionId = req.getSession().getId();
        CustomerData customerData = customerDataStore.getByName(sessionId);

        //TODO maybe this should be moved to BaseController
        if (customerData == null) {
            customerData = new CustomerData(sessionId);

            customerData.setCustomerName(req.getParameter("name"));
            customerData.setCustomerEmail(req.getParameter("email"));
            customerData.setCustomerPhoneNumber(req.getParameter("phone"));
            customerData.setBillingAddress(req.getParameter("billing-address"));
            customerData.setBillingAddressCountry(req.getParameter("billing-country"));
            customerData.setBillingAddressCity(req.getParameter("billing-city"));
            customerData.setBillingAddressZipCode(req.getParameter("billing-zip-code"));
            customerData.setShippingAddress(req.getParameter("shipping-address"));
            customerData.setShippingAddressCountry(req.getParameter("shipping-country"));
            customerData.setShippingAddressCity(req.getParameter("shipping-city"));
            customerData.setShippingAddressZipCode(req.getParameter("shipping-zip-code"));
            customerData.setUserId((int) req.getSession().getAttribute("user_id"));

            customerDataStore.add(customerData);
        }

        //redirect
        resp.sendRedirect("/payment");

    }

    private void setContextVariables(Cart cart, String sessionID) {
        // TODO: check if refactorable
        setUserNameToContext();
        context.setVariable("userId", sessionID);
        context.setVariable("itemsInCart", cart.getTotalProductCount());
        context.setVariable("cart", cart);
    }
}
