package com.codecool.shop.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Cart extends BaseModel {

    private int userId = 1;
    private Map<Product, Integer> productsWithQuantityList = new HashMap<>();

    public Cart(String name) {
        super(name);
    }
    public Cart(int userId) {
        super(String.valueOf(userId));
    }

    public Map<Product, Integer> getProductsWithQuantityList() {
        return productsWithQuantityList;
    }

    public void addProduct(Product product, int quantity) {
        boolean isProductInList = this.productsWithQuantityList.keySet().contains(product);

        if(isProductInList) {
            this.productsWithQuantityList.put(product, productsWithQuantityList.get(product) + quantity);
        } else {
            this.productsWithQuantityList.put(product, quantity);
        }
    }


    public int getTotalProductCount() {
        int total = 0;
        for (int quantity: productsWithQuantityList.values()) {
            total += quantity;
        }
        return total;
    }

    public BigDecimal getSumPrice() {

        BigDecimal total = new BigDecimal(0);


        for (Product product : this.productsWithQuantityList.keySet()) {
            total = total.add(getProductTotal(product));
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getProductTotal(Product product) {

        BigDecimal countOfGivenProduct = new BigDecimal(productsWithQuantityList.get(product));

        BigDecimal total = countOfGivenProduct.multiply(new BigDecimal(product.getDefaultPrice()));

        return total.setScale(2, RoundingMode.HALF_UP);

    }

    public void modifyProductQuantity(Product product, int quantity) {
        this.productsWithQuantityList.put(product, quantity);

    }

    public void removeProduct(Product prod) {
        productsWithQuantityList.remove(prod);
    }

    public String getSumPriceJson() {

        return "{" +
                '"' + "order" + '"' + ":" + '"' + getSumPrice() + '"' +
                '}';
    }

    public void cleanCart() {
        productsWithQuantityList = new HashMap<>();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
