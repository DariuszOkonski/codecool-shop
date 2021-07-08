package com.codecool.shop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public BigDecimal getSumPrice() {

        BigDecimal total = new BigDecimal(0);

        for (Product product : this.productsWithQuantityList.keySet()) {
            BigDecimal price = new BigDecimal(product.getPrice());
            BigDecimal quantity = new BigDecimal(productsWithQuantityList.get(product));
            total.add(price.multiply(quantity));
        }

        return total;
    }

}
