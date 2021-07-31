package com.codecool.shop.dao;

import com.codecool.shop.model.User;

public interface UserDao {


    int createNewGuest();
    void add(String name, String email, String password);
    User getUserByCredentials(String name, String password);
    User getById(int userId);
    boolean doesGivenEmailExists(String email);
    boolean doesGivenUserExists(String user);
    String getPasswordOfUser(String username);

}
