package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoMem implements UserDao {
    private List<User> users = new ArrayList<>();
    private static UserDaoMem INSTANCE;

    public static UserDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDaoMem();
        }
        return INSTANCE;
    }

    @Override
    public int createNewGuest() {
        int nextId = users.size()+1;
        add(String.format("guest%d", nextId), "");
        return nextId;
    }

    @Override
    public void add(String name, String password) {
        User user = new User(name, password);
        user.setId(users.size() + 1);
        users.add(user);
    }

    @Override
    public User getUserByCredentials(String name, String password) {
        return users.stream()
                .filter(user -> user.equals(new User(name, password)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User getById(int userId) {
        return users.stream()
                .filter(user -> user.getId() == userId)
                .findFirst()
                .orElse(null);
    }
}
