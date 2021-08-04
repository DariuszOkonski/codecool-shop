package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.CustomerDataDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.payment.PaymentMethods;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderDaoJdbc implements OrderDao {

    private DataSource dataSource;

    public OrderDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Order order) {
        try(Connection conn = dataSource.getConnection()){
            String sql = "INSERT INTO \"order\" (cart_id, order_status_id, user_id, order_number) VALUES (?, 1, ?, 'asd')";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, order.getCustomerCart().getId());
            statement.setInt(2, order.getCustomerCart().getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> getAllOrdersForSpecificUser(int user_id) {

/**       - There is an `Order history` menu item
 - provide a list with all the Orders of that user, with the following details:
 - order date
 - order status (checked / paid / confirmed / shipped)
 - total price
 - product list (with product name, price)

 */

        //get orders list from db
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT o.order_date, o.id, order_number, cart_id, payment_method, os.name " +
                    "FROM \"order\" o " +
                    "INNER JOIN \"user\" u ON user_id = u.id " +
                    "INNER JOIN \"order_status\" os on o.order_status_id = os.id " +
                    "WHERE user_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, user_id);

            // make it a list

            ResultSet rs = statement.executeQuery();
            List<Order> orderList = new ArrayList<>();
            CartDao cartDao = DatabaseManager.getINSTANCE().getCartDao();
            CustomerDataDao customerDataDao = DatabaseManager.getINSTANCE().getCustomerDataDao();

            while (rs.next()) {
                int cart_id = rs.getInt("cart_id");
                var cart = cartDao.find(cart_id);
                var customerData = customerDataDao.find(user_id);
                var order = new Order(customerData, cart);
                order.setDateTime(rs.getDate("order_date"));
                orderList.add(order);
            }


            return orderList;
        } catch (SQLException e) {
            return null;
        }

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Order find(int id) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = " SELECT  cart_id, user_id, id, payment_method, order_status_id  FROM \"order\" WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();

            int cart_id = rs.getInt(1);
            int user_id = rs.getInt(2);
            int ord_id = rs.getInt(3);
            String payment_method = rs.getString(4);
            int status_id = rs.getInt(5);

            Order order = new Order(
                    DatabaseManager.getINSTANCE().getCustomerDataDao()
                            .find(user_id),
                    DatabaseManager.getINSTANCE().getCartDao()
                            .find(cart_id)
            );
            order.setId(ord_id);

            if (payment_method != null) {
                order.setPayment(
                        PaymentMethods.build(
                                payment_method,
                                order.getCustomerCart().getSumPrice(),
                                order.getId()
                        )
                );
                order.getPayment().setFinished(status_id == FINISHED_ID);
            }
            return order;
        } catch (SQLException e){
            return null;
        }
    }

    @Override
    public Order getByName(String userName) {
        return null;
    }

    @Override
    public Order getNewestOfUser(int userId) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = " SELECT  cart_id, user_id, id, payment_method, order_status_id  FROM \"order\" WHERE user_id=? ORDER BY id desc; ";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            rs.next();

            int cart_id = rs.getInt(1);
            int user_id = rs.getInt(2);
            int ordId = rs.getInt(3);
            String payment_method = rs.getString(4);
            int status_id = rs.getInt(5);

            Order order = new Order(
                    DatabaseManager.getINSTANCE().getCustomerDataDao()
                            .find(user_id),
                    DatabaseManager.getINSTANCE().getCartDao()
                            .find(cart_id)
            );
            order.setId(ordId);

            if (payment_method != null) {
                order.setPayment(
                        PaymentMethods.build(
                                payment_method,
                                order.getCustomerCart().getSumPrice(),
                                order.getId()
                        )
                );
                order.getPayment().setFinished(status_id == FINISHED_ID);
            }
            return order;
        } catch (SQLException e){
            return null;
        }
    }

    @Override
    public void setPaymentStatus(Order ord, boolean paymentPossible) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = " UPDATE \"order\" SET order_status_id=? WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, paymentPossible ? 2 : 1);
            statement.setInt(2, ord.getId());
            statement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setPaymentMethod(Order ord, String method) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = " UPDATE \"order\" SET payment_method=? WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, method);
            statement.setInt(2, ord.getId());
            statement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
