package com.codecool.shop.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.text.DecimalFormat;

public class Cart extends BaseModel {

    private HashMap<Product, Integer> productsWithQuantityList = new HashMap<>();

    public Cart(String name) {
        super(name);
    }

    public HashMap<Product, Integer> getProductsWithQuantityList() {
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
            BigDecimal price = new BigDecimal(product.getDefaultPrice());
            BigDecimal quantity = new BigDecimal(productsWithQuantityList.get(product));
            total.add(price.multiply(quantity));
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getProductTotal(Product product) {

        BigDecimal countOfGivenProduct = new BigDecimal(productsWithQuantityList.get(product));

        BigDecimal total = countOfGivenProduct.multiply(new BigDecimal(product.getDefaultPrice()));

        return total.setScale(2, RoundingMode.HALF_UP);

    }

}
