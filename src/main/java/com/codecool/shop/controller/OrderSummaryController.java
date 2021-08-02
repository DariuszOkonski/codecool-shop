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
    private FileService csvFileService = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setTemplateContext(req, resp);
        serviceSessionValidation(req);

        // TODO: object Order is Given here by GET request
//        order = new Order("gliwice", "companyABC", "darek200180@gmail.com", null);
//        order.setPaymentSuccessfull();

        //        System.out.println(req.getSession().getAttribute("processed_order") + " PRCESSD ORDER ");
        // TODO MOVE FROM SESSION ID BASED PROCESSING TO SETTING ORDER ID IN SESSION
        int userId = (int) req.getSession().getAttribute("user_id");
        setLoggedUsername(userId);

        int orderId = (int) req.getSession().getAttribute("order_id");
        order = orderDataStore.find(orderId);

        emailService = new EmailService();
        jsonService = new JSONService();
        csvFileService = new CSVFileService();

        if(order == null) {
            sendErrorOrder(resp, req);
        }else if(!order.isPaymentSuccessfull()) {
            try {
                sendFailedOrder(resp, req);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                sendSuccessfulOrder(resp, req);
                setNewCart(req);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    }

    private void sendErrorOrder(HttpServletResponse resp, HttpServletRequest req) throws IOException {
        System.out.println("--- ERROR ---");
        String status = "ERROR";
        String message = "Some Error occurred";
        String orderNumber = "No number";
        setContextVariables(status, message, orderNumber, req);
        engine.process("product/orderSummary.html", context, resp.getWriter());
    }

    private void sendFailedOrder(HttpServletResponse resp, HttpServletRequest req) throws IOException, MessagingException {
        System.out.println("--- ORDER DENIED ---");
        String status = "ORDER DENIED";
        String message = "No Payment has been made";
        String orderNumber = order.getCustomerName();
        String email = order.getEmail();

        // ??? send en email about refusal of the order - not in specification
        emailService.sendConfirmation(orderNumber, message, email);

        // display page with payment refusal and error details, and possibility to return to basket or to main page
        setContextVariables(status, message, orderNumber, req);
        engine.process("product/orderSummary.html", context, resp.getWriter());
    }

    private void sendSuccessfulOrder(HttpServletResponse resp, HttpServletRequest req) throws IOException, MessagingException {
        System.out.println("--- ORDER SUCCESSFULL ---");
        String status = "ORDER ACCEPTED";
        String message = "Payment has been successfull";
        String orderNumber = order.getCustomerName();
        String email = order.getEmail();

        // if ok, save order to json file
        String convertedJsonOrder = jsonService.convertData(order);
        csvFileService.saveToFile("json.csv", convertedJsonOrder);
        System.out.println(order);

        // logic to send email that payment was ok
        emailService.sendConfirmation(orderNumber, message, email);

        // dispaly page with conformation and details of the order, and possibility to get back to main page
        setContextVariables(status, message, orderNumber, req);
        engine.process("product/orderSummary.html", context, resp.getWriter());
    }

    private void setContextVariables(String status, String message, String orderNumber, HttpServletRequest req) {
        setUserNameToContext();
        context.setVariable("status", status);
        context.setVariable("itemsInCart",
                cartDataStore
                        .getNewestOfUser((int)req.getSession().getAttribute("user_id"))
                        .getTotalProductCount());
        context.setVariable("message", message);
        context.setVariable("orderNumber", orderNumber);
    }
}
