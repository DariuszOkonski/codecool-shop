package com.codecool.shop.dao;

import com.codecool.shop.model.User;

import java.sql.Timestamp;

public interface UserDao {


    int createNewGuest();
    void add(String name, String email, String password);
    User getUserByCredentials(String name, String password);
    User getById(int userId);
    boolean doesGivenEmailExists(String email);
    boolean doesGivenUserExists(String user);
    String getPasswordOfUser(String username);
    Integer getUserIdByEmail(String email);
    void storeUserSessionInfo(int userId, String sessionToken, Timestamp expiration);

}
