package com.codecool.shop.controller;

import com.codecool.shop.service.PasswordService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends BaseController{
    private PasswordService ps = new PasswordService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String user = req.getParameter("username");
        String password = req.getParameter("password");

        String passwordHash = userDataStore.getPasswordOfUser(user);

        // GOING TRUE - seems ok prototype
        System.out.println(ps.passwordMatches(password, passwordHash) + " !!!!!!! PASSWORD CHECK");
    }
}
