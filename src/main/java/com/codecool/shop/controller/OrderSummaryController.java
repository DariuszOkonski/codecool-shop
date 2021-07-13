package com.codecool.shop.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/order-summary"})
public class OrderSummaryController extends BaseController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
//        boolean isPaymentSuccessfull = req.getParameter("")
    }
}
