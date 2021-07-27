package com.codecool.shop.controller;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet(urlPatterns = {"/modify-cart"})
public class ModifyCartController extends BaseController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setTemplateContext(req, resp);
        serviceSessionValidation(req);

        try {
            JSONObject jsonParameters = readJSONParameters(req);
            String prodId = jsonParameters.getString("prodId");
            String quantity = jsonParameters.getString("newQuantity");
            System.out.println(prodId + " " + quantity);
            updateCart(prodId, quantity, req);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private JSONObject readJSONParameters(HttpServletRequest req) throws JSONException, IOException {
        BufferedReader br =
                new BufferedReader(new InputStreamReader(req.getInputStream()));

        String json = "";

        JSONObject parameters = null;

        if(br != null){
            json = br.readLine();
            parameters = new JSONObject(json);
        }

        return parameters;
    }

    private void updateCart(String productId, String quantity, HttpServletRequest req) {
        int user_id = (int) req.getSession().getAttribute("user_id");
        cartDataStore.addProduct(
                cartDataStore.getNewestOfUser(user_id),
                productDataStore.find(Integer.parseInt(productId)),
                Integer.parseInt(quantity));

    }


}
