package com.codecool.shop.service;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.jdbc.CartDaoJdbc;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

public class CartService {
    private CartDao cartDao;
    private ProductDao productDao;

    public CartService(CartDao cartDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    public void removeProduct(int product_id, int user_id) {
        Cart newestCart = cartDao.getNewestOfUser(user_id);
        Product product = productDao.find(product_id);

        cartDao.removeProduct(newestCart, product);
    }
}
