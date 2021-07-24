package com.codecool.shop.controller.endpoints;

import com.codecool.shop.controller.BaseController;
import com.codecool.shop.model.OrderModel;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/get-order-value"})
public class SumOrderProvider extends BaseController{

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            setTemplateContext(req, resp);
            serviceSessionValidation(req);

            OrderModel sumPriceJson = new OrderModel(cartDataStore.getBySessionId(req.getSession().getId()));
            String employeeJsonString = new Gson().toJson(sumPriceJson);

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(employeeJsonString);
            out.flush();
        }


}
