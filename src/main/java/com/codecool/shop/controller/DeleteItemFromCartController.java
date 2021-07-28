package com.codecool.shop.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(urlPatterns = {"/delete-item"})
public class DeleteItemFromCartController extends BaseController{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setTemplateContext(req, resp);
        serviceSessionValidation(req);

        int productId = Integer.parseInt(req.getParameter("product_id"));


        int user_id = (int)req.getSession().getAttribute("user_id");
        cartService.removeProduct(productId, user_id);

//        String referrer =  req.getHeader("referer");
        resp.sendRedirect("/cart");

    }
}
