package com.codecool.shop.controller;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.CustomerData;
import com.codecool.shop.model.payment.CreditCard;
import com.codecool.shop.model.payment.PaymentMethod;
import com.codecool.shop.model.payment.PaymentMethods;
import com.codecool.shop.model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;


@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends BaseController{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setTemplateContext(req, resp);
        serviceSessionValidation(req);

        String currentSession = req.getSession().getId();
        Cart currentCart = cartDataStore.getByName(currentSession);
        CustomerData customerData = customerDataStore.getByName(currentSession);

        Order order;

        if (orderDataStore.getByName(currentSession) == null){
            order = new Order(customerData, currentCart);
            orderDataStore.add(order);
        } else {
            order = orderDataStore.getByName(currentSession);
        }
        req.getSession().setAttribute("processed_order", order.getId());
        BigDecimal amountToPay = currentCart.getSumPrice();

        String chosenPaymentMethod = req.getParameter("method");
        if (chosenPaymentMethod != null) {
            PaymentMethod paymentMethod = PaymentMethods.build(chosenPaymentMethod, BigDecimal.ONE, currentSession);
            order.setPayment(paymentMethod);
        }
        setContextVariables(currentSession, amountToPay, chosenPaymentMethod, order.getCustomerCart().getSumPrice(), order.getCustomerCart());


        engine.process(getPaymentMethodTemplate(chosenPaymentMethod), context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (PaymentMethods.valueOfUrl(req.getParameter("method"))) {
            case CREDIT_CARD:
                serviceCreditCardPayment(req, resp);
            case TRANSFER:
//                serviceCreditCardPayment(req);
            case PAY_PAL:
//                serviceCreditCardPayment(req);
            default:

        }
        //redirect to result page

    }

    private void serviceCreditCardPayment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cardNumber = req.getParameter("cardNumberInput");
        String expYear = req.getParameter("expirationYear");
        String expMonth = req.getParameter("expirationMonth");
        String cvv = req.getParameter("cvv");

        Order ord = orderDataStore.getByName(req.getSession().getId());
        CreditCard card = new CreditCard(cardNumber, expYear, expMonth, cvv);
        if (card.isDataCorrect() && card.fundsEnoughFor(ord.getCustomerCart().getSumPrice())) {
            ord.setPaymentSuccessfull(true);

            //redirect to main page
        } else {
            ord.setPaymentSuccessfull(false);
        }
        // TODO MOVE FROM SESSION ID BASED PROCESSING TO SETTING ORDER ID IN SESSION
        req.getSession().setAttribute("processed_order", ord.getId());
        resp.sendRedirect(String.format("/order-summary?order_id=%s", ord.getId()));
    }

    private void servicePayPalPayment(HttpServletRequest req, HttpServletResponse resp) throws IOException{

    }

    private void setContextVariables(String sessionID, BigDecimal cartSumPrice, String chosenPaymentMethod, BigDecimal sumToPay, Cart cart) {
        // TODO: check if refactorable
        context.setVariable("userId", sessionID);
        context.setVariable("amountToPay", cartSumPrice);
        context.setVariable("itemsInCart", cart.getTotalProductCount());
        context.setVariable("sumToPay", sumToPay);
        if (chosenPaymentMethod == null) {
            context.setVariable("paymentMethods", PaymentMethods.values());
        }
    }

    private String getPaymentMethodTemplate(String chosenMethod){
        return chosenMethod != null ? "product/payments/" + chosenMethod +".html" : "product/payment.html";
    }
}
