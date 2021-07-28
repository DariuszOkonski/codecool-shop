package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartDaoJdbc implements CartDao {
    private DataSource dataSource;

    public CartDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Cart cart) {
        try(Connection conn = dataSource.getConnection()){
            String sql = "INSERT INTO cart (user_id, session_id) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, cart.getUserId());
            statement.setString(2, cart.getName());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addProduct(Cart cart, Product product, int quantity) {
        try(Connection conn = dataSource.getConnection()){
            String sqlUpdate =
                    "UPDATE cart_items SET product_quantity=? WHERE product_id=? AND cart_id=?; "+
                    " INSERT INTO cart_items (product_id, cart_id, product_quantity) SELECT ?, ?, ? " +
                            " WHERE NOT EXISTS (SELECT 1 FROM cart_items WHERE product_id=? AND cart_id=?); ";


            PreparedStatement statementUpdate = conn.prepareStatement(sqlUpdate);
//            PreparedStatement statementInsert = conn.prepareStatement(sqlInsert);

            statementUpdate.setInt(1, quantity);
            statementUpdate.setInt(2, product.getId());
            statementUpdate.setInt(3, cart.getId());

            statementUpdate.setInt(4, product.getId());
            statementUpdate.setInt(5, cart.getId());
            statementUpdate.setInt(6, quantity);
            statementUpdate.setInt(7, product.getId());
            statementUpdate.setInt(8, cart.getId());
            statementUpdate.executeUpdate();

//            String sqlUpsert = "INSERT INTO cart_items (product_id, cart_id, product_quantity) VALUES (?, ?, ?) " +
//                    "ON CONFLICT (product_id) DO UPDATE " +
//                    "  SET product_quantity = (?) WHERE cart_items.cart_id=(?) AND cart_items.product_id=(?);";
//            PreparedStatement statement = conn.prepareStatement(sqlUpsert);
//            statement.setInt(1, product.getId());
//            statement.setInt(2, cart.getId());
//            statement.setInt(3, quantity);
//            statement.setInt(4, quantity);
//            statement.setInt(5, cart.getId());
//            statement.setInt(6, product.getId());
//            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeProduct(Cart cart, Product product) {
        try(Connection conn = dataSource.getConnection()){
            String sql = "DELETE FROM cart_items WHERE cart_id=? AND product_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, cart.getId());
            statement.setInt(2, product.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {
        try(Connection conn = dataSource.getConnection()){
            String sql = "DELETE FROM cart_items WHERE cart_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

            String sqlCart = "DELETE FROM cart WHERE id=(?)";
            PreparedStatement statementCart = conn.prepareStatement(sqlCart);
            statementCart.setInt(1, id);
            statementCart.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cart find(int id) {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT * from cart where id=(?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            Cart cart = new Cart(rs.getString(3));
            cart.setId(rs.getInt(1));
            cart.setUserId(rs.getInt(2));

            String sqlItems = "SELECT product_id, product_quantity From cart_items where cart_id = (?)";
            PreparedStatement statementItems = conn.prepareStatement(sqlItems);
            statementItems.setInt(1, cart.getId());
            ResultSet rsItems = statementItems.executeQuery();
            while(rsItems.next()){
                Product product = DatabaseManager.getINSTANCE().getProductDao().find(rsItems.getInt(1));
                cart.addProduct(product, rsItems.getInt(2));
            }
            return cart;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Cart getBySessionId(String name) {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT * from cart where session_id=(?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            rs.next();
            Cart cart = new Cart(rs.getString(3));
            cart.setId(rs.getInt(1));
            cart.setUserId(rs.getInt(2));


            String sqlItems = "SELECT product_id, product_quantity From cart_items where cart_id = (?)";
            PreparedStatement statementItems = conn.prepareStatement(sqlItems);
            statementItems.setInt(1, cart.getId());
            ResultSet rsItems = statementItems.executeQuery();
            while(rsItems.next()){
                Product product = DatabaseManager.getINSTANCE().getProductDao().find(rsItems.getInt(1));
                cart.addProduct(product, rsItems.getInt(2));
            }
            return cart;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Cart getNewestOfUser(int id) {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT * from cart where user_id=(?) ORDER BY id DESC";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            Cart cart = new Cart(rs.getString(3));
            cart.setId(rs.getInt(1));
            cart.setUserId(rs.getInt(2));

            String sqlItems = "SELECT product_id, product_quantity From cart_items where cart_id = (?)";
            PreparedStatement statementItems = conn.prepareStatement(sqlItems);
            statementItems.setInt(1, cart.getId());
            ResultSet rsItems = statementItems.executeQuery();
            while(rsItems.next()){
                Product product = DatabaseManager.getINSTANCE().getProductDao().find(rsItems.getInt(1));
                cart.addProduct(product, rsItems.getInt(2));
            }
            return cart;
        } catch (SQLException throwables) {
            return null;
        }
    }
}
