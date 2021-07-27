package com.codecool.shop.model;

import java.util.Objects;

public class User extends BaseModel{
    private String password;
    public User(String name) {
        super(name);
    }
    public User(String name, String password){
        super(name);
        this.password = password;
    }


    public boolean equals(User o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = o;
        return Objects.equals(password, user.password) && Objects.equals(getName(), user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
