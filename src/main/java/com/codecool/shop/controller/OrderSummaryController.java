package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.service.ProductService;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/order-summary"})
public class OrderSummaryController extends BaseController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setTemplateContext(req, resp);

        // here I will recive object Order, but have temporary mock
        Order order = new Order("123", "gliwice", "companyABC", "darek200180@gmail.com", null);

        if(!order.isPaymentSuccessfull()) {
            String message = "No Payment has been made";
            String orderNumber = order.getOrderNumber();

            setContextVariables(message, orderNumber);
            engine.process("product/cart.html", context, resp.getWriter());

        } else {
            // order successfull
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
//        boolean isPaymentSuccessfull = req.getParameter("")
    }

    private void setContextVariables(String message, String orderNumber) {
        // TODO: check if refactorable
        context.setVariable("message", message);
        context.setVariable("orderNumber", orderNumber);
    }
}
