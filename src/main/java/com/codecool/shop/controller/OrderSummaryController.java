package com.codecool.shop.controller;

import com.codecool.shop.model.Order;
import com.codecool.shop.service.*;

import javax.mail.MessagingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/order-summary"})
public class OrderSummaryController extends BaseController {
    private Order order = null;
    private MessageService emailService = null;
    private ReportService jsonService = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setTemplateContext(req, resp);
        serviceSessionValidation(req);

        // TODO: object Order is Given here by GET request
        order = new Order("gliwice", "companyABC", "darek200180@gmail.com", null);
        order.setPaymentSuccessfull();

        //        System.out.println(req.getSession().getAttribute("processed_order") + " PRCESSD ORDER ");
        // TODO MOVE FROM SESSION ID BASED PROCESSING TO SETTING ORDER ID IN SESSION
//        int orderId = Integer.parseInt(req.getParameter("order_id"));
//        order = orderDataStore.find(orderId);

        emailService = new EmailService();
        jsonService = new JSONService();

        if(order == null) {
            sendErrorOrder(resp);
        }else if(!order.isPaymentSuccessfull()) {
            try {
                sendFailedOrder(resp);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                sendSuccessfulOrder(resp);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    }

    private void sendErrorOrder(HttpServletResponse resp) throws IOException {
        System.out.println("--- ERROR ---");
        String status = "ERROR";
        String message = "Some Error occurred";
        String orderNumber = "No number";
        setContextVariables(status, message, orderNumber);
        engine.process("product/orderSummary.html", context, resp.getWriter());
    }

    private void sendFailedOrder(HttpServletResponse resp) throws IOException, MessagingException {
        System.out.println("--- ORDER DENIED ---");
        String status = "ORDER DENIED";
        String message = "No Payment has been made";
        String orderNumber = order.getCustomerName();
        String email = order.getEmail();

        // ??? send en email about refusal of the order - not in specification
        emailService.sendConfirmation(orderNumber, message, email);

        // display page with payment refusal and error details, and possibility to return to basket or to main page
        setContextVariables(status, message, orderNumber);
        engine.process("product/orderSummary.html", context, resp.getWriter());
    }

    private void sendSuccessfulOrder(HttpServletResponse resp) throws IOException, MessagingException {
        System.out.println("--- ORDER SUCCESSFULL ---");
        String status = "ORDER ACCEPTED";
        String message = "Payment has been successfull";
        String orderNumber = order.getCustomerName();
        String email = order.getEmail();

        // if ok, save order to json file
        String convertedJsonOrder = jsonService.convertData(order);
        System.out.println(convertedJsonOrder);


        // logic to send email that payment was ok
        emailService.sendConfirmation(orderNumber, message, email);

        // dispaly page with conformation and details of the order, and possibility to get back to main page
        setContextVariables(status, message, orderNumber);
        engine.process("product/orderSummary.html", context, resp.getWriter());
    }

    private void setContextVariables(String status, String message, String orderNumber) {
        context.setVariable("status", status);
        context.setVariable("message", message);
        context.setVariable("orderNumber", orderNumber);
    }
}
