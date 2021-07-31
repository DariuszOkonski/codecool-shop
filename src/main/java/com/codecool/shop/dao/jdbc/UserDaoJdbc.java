package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDaoJdbc implements UserDao {
    private DataSource ds;
    public UserDaoJdbc(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public int createNewGuest() {
        int nextId=getNextId();
        add(String.format("guest%d", nextId), String.format("guest%d@cc_shop.pl", nextId), "");
        return nextId;
    }

    @Override
    public void add(String name, String email, String password) {
        try(Connection conn = ds.getConnection()){
            String sql = "INSERT  INTO public.\"user\" (username, email, password_hash) SELECT ?,?,? " +
                    "WHERE NOT EXISTS (SELECT 1 FROM public.\"user\" WHERE username=?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, name);
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

    public Integer getUserIdByEmail(String email) {
        try(Connection conn = ds.getConnection()){
            String sql = "SELECT id FROM public.\"user\" WHERE email=(?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
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

    @Override
    public boolean doesGivenEmailExists(String email) {
        try(Connection conn = ds.getConnection()){
            String sql = "SELECT email FROM public.\"user\" WHERE email=(?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getPasswordOfUser(String email) {
        try(Connection conn = ds.getConnection()){
            String sql = "SELECT password_hash FROM public.\"user\" WHERE email=(?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next());
                return rs.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean doesGivenUserExists(String username) {
        try(Connection conn = ds.getConnection()){
            String sql = "SELECT username FROM public.\"user\" WHERE username=(?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public void storeUserSessionInfo(int userId, String sessionToken, Timestamp expiration) {
        try(Connection conn = ds.getConnection()){
            String sql = "INSERT INTO user_session (user_id, session_expiration, session_token)" +
                    " VALUES (?, ?, ?) ";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setTimestamp(2, expiration);
            statement.setString(3, sessionToken);
            System.out.println(statement);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
