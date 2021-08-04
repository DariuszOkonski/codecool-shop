package com.codecool.shop.controller;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Order;
import com.codecool.shop.service.CSVFileService;
import com.codecool.shop.service.EmailService;
import com.codecool.shop.service.JSONService;

import javax.mail.MessagingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/order-history"})
public class OrderHistoryController extends BaseController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setTemplateContext(req, resp);
        serviceSessionValidation(req);
        int user_id = (int) req.getSession().getAttribute("user_id");

        setLoggedUsername(user_id);
        setUserNameToContext();

        context.setVariable("itemsInCart", cartDataStore.getNewestOfUser(user_id).getTotalProductCount());

        List<Order> orderList = orderDataStore.getAllOrdersForSpecificUser(user_id);

        context.setVariable("orderList", orderList);

        engine.process("product/orderHistory.html", context, resp.getWriter());
    }


}
