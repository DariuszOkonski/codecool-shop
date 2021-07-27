package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoJdbc implements UserDao {
    private DataSource ds;
    public UserDaoJdbc(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public int createNewGuest() {
        int nextId=getNextId();
        add(String.format("guest%d", nextId), "");
        return nextId;
    }

    @Override
    public void add(String name, String password) {
        try(Connection conn = ds.getConnection()){
            String sql = "INSERT  INTO public.\"user\" (username, password_hash) SELECT ?,? " +
                    "WHERE NOT EXISTS (SELECT 1 FROM public.\"user\" WHERE username=?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, password);
            statement.setString(3, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByCredentials(String name, String password) {
        try(Connection conn = ds.getConnection()){
            String sql = "SELECT id, username FROM public.\"user\" WHERE username=(?) AND password_hash=(?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString(2));
                user.setId(rs.getInt(1));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User getById(int userId) {
        try(Connection conn = ds.getConnection()){
            String sql = "SELECT id, username FROM public.\"user\" WHERE id=(?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString(2));
                user.setId(rs.getInt(1));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private int getNextId(){
        try(Connection conn = ds.getConnection()){
            String sql = "SELECT MAX(id) as id FROM public.\"user\"";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("id")+1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
