package com.codecool.shop.controller;

import com.codecool.shop.service.CSVFileService;
import com.codecool.shop.service.EmailService;
import com.codecool.shop.service.JSONService;

import javax.mail.MessagingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/order-history"})
public class OrderHistoryController extends BaseController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setTemplateContext(req, resp);
        serviceSessionValidation(req);



        engine.process("product/orderHistory.html", context, resp.getWriter());
    }

}
