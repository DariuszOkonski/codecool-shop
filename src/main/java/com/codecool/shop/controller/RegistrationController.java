package com.codecool.shop.controller;

import com.codecool.shop.model.Cart;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/registration"})
public class RegistrationController extends BaseController {
    private final String ERROR_MSG = "error_message";
    private final String SUCCESS_MSG = "success_message";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        setTemplateContext(req, resp);
        serviceSessionValidation(req);

        int userId = (int) req.getSession().getAttribute("user_id");
        String sessionId = req.getSession().getId();

        String errorMessage = req.getSession().getAttribute(ERROR_MSG) != null ? req.getSession().getAttribute(ERROR_MSG).toString() : "";
        String successMessage = req.getSession().getAttribute(SUCCESS_MSG) != null ? req.getSession().getAttribute(SUCCESS_MSG).toString() : "";

        setContextVariables(cartDataStore.getNewestOfUser(userId), sessionId, errorMessage, successMessage);

        engine.process("/register.html", context, resp.getWriter());
    }

        @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String user = req.getParameter("username");
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            boolean isEmailExists = userDataStore.doesGivenEmailExists(email);
            boolean isUserNameExists = userDataStore.doesGivenUserExists(user);

            System.out.println("check email: " + isEmailExists);
            System.out.println("check user: " + isUserNameExists);

            req.getSession().removeAttribute(ERROR_MSG);
            req.getSession().removeAttribute(SUCCESS_MSG);


            String messageType = "";
            String message = "";
            if(isEmailExists ) {
                message = "emailExists";
                messageType = ERROR_MSG;
            } else if(isUserNameExists) {
                message = "userExists";
                messageType = ERROR_MSG;
            } else {
                message = "success";
                messageType = SUCCESS_MSG;
            }

            req.getSession().setAttribute(messageType, message);
            resp.sendRedirect("/registration");



            // TODO check if user exists in db by email
            // ToDO if exist display message


            // TODO make register service (hash password)
            // TODO add to database
            // TODO alter table user add email field
            // TODO redirect to main page
    }

    private void setContextVariables(Cart cart, String sessionID, String errorMessage, String successMessage) {
        // TODO: check if refactorable
        context.setVariable("userId", sessionID);
        context.setVariable("itemsInCart", cart.getTotalProductCount());
        context.setVariable("cart", cart);
        context.setVariable(ERROR_MSG, errorMessage);
        context.setVariable(SUCCESS_MSG, successMessage);
    }
}
