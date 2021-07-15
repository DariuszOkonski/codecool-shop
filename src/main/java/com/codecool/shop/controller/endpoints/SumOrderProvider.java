package com.codecool.shop.controller.endpoints;

import com.codecool.shop.controller.BaseController;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.payment.CreditCard;
import com.codecool.shop.model.payment.PaymentMethod;
import com.codecool.shop.model.payment.PaymentMethods;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.PrintWriter;
import java.math.BigDecimal;
@WebServlet(urlPatterns = {"/get-order-value"})
public class SumOrderProvider extends BaseController{

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            setTemplateContext(req, resp);
            serviceSessionValidation(req);

            class mock {
                BigDecimal order = orderDataStore.find(Integer.parseInt(req.getParameter("order-id")))
                        .getCustomerCart()
                        .getSumPrice();

                public mock() {
                }

                @Override
                public String toString() {
                    return "{" +
                            "\"order\":" + "\"" + order +"\""+
                            '}';
                }
            }
            mock kruwa = new mock();
            String employeeJsonString = kruwa.toString();

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(employeeJsonString);
            out.flush();
        }


}
