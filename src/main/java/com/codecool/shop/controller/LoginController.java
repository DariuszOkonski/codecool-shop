package com.codecool.shop.controller;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Order;
import com.codecool.shop.service.PasswordService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends BaseController{
    private PasswordService ps = new PasswordService();

    private final String ERROR_MSG = "error_message";
    private final String SUCCESS_MSG = "success_message";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        setTemplateContext(req, resp);
        serviceSessionValidation(req);

        int userId = (int) req.getSession().getAttribute("user_id");
        setLoggedUsername(userId);

        String sessionToken = (String) req.getSession().getAttribute("token");

        System.out.println("FOUND TOKEN: " + sessionToken);

        String sessionId = req.getSession().getId();
        setContextVariables(cartDataStore.getNewestOfUser(userId), sessionId, null, null);


        engine.process("/login.html", context, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            String passwordHash = userDataStore.getPasswordOfUser(email);

            // GOING TRUE - seems ok prototype
            System.out.println(ps.passwordMatches(password, passwordHash) + " !!!!!!! PASSWORD CHECK");

            boolean areCredentialsOk = ps.passwordMatches(password, passwordHash);

            //store session in db, generate token, send it to browser
            if (areCredentialsOk) setSession(req, email);

            resp.sendRedirect("/");
        } catch (Exception e) {
            //TODO: can be done better
            resp.sendRedirect("/login");
        }
    }

    private void setSession(HttpServletRequest request, String email){
        String token = ps.generateToken();
        System.out.println("TOKEN: " + token);
        int userId = userDataStore.getUserIdByEmail(email);
        System.out.println("USERID: " + userId);
        Timestamp expirationDate = ps.getExpirationDate();
        System.out.println("Expiration: " + expirationDate);

        userDataStore.storeUserSessionInfo(userId, token, expirationDate);

        // store token in browser cookie. not sure if works.
        request.getSession().setAttribute("token", token);
        request.getSession().getAttribute("token");
        request.getSession().setAttribute("user_id", userId);
        setNewCartIfOldAreFinished(userId);
    }

    private void setContextVariables(Cart cart, String sessionID, String errorMessage, String successMessage) {
        // TODO: check if refactorable
        setUserNameToContext();
        context.setVariable("userId", sessionID);
        context.setVariable("itemsInCart", cart.getTotalProductCount());
        context.setVariable("cart", cart);
        context.setVariable(ERROR_MSG, errorMessage);
        context.setVariable(SUCCESS_MSG, successMessage);
    }

    private void setNewCartIfOldAreFinished(int userId){
        Cart cart = cartDataStore.getNewestOfUser(userId);
        if (cart == null) {
            setNewCart(userId);
        }

    }
}
