package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.CustomerDataDao;
import com.codecool.shop.model.CustomerData;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDataDaoJdbc implements CustomerDataDao {
    private DataSource dataSource;

    public CustomerDataDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public CustomerData getByName(String name) {
        try(Connection conn = dataSource.getConnection()){
            String sqlCountry = "SELECT user_id, session_id, customer_name, customer_phone_number, customer_email, billing_address_id, shipping_address_id " +
                    "FROM customer_data  " +
                    "WHERE session_id=?";
            PreparedStatement statement = conn.prepareStatement(sqlCountry);
            statement.setString(1, name);
            ResultSet customer_data = statement.executeQuery();
            boolean next = customer_data.next();
            CustomerData customer = new CustomerData(customer_data.getString("session_id"));
            customer.setUserId(customer_data.getInt("user_id"));
            return customer;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void add(CustomerData customerData) {
        try(Connection conn = dataSource.getConnection()){


            String sqlCountry = "INSERT INTO country (country_name) SELECT (?) " +
                    "WHERE NOT EXISTS (SELECT 1 FROM country WHERE country_name=(?))";
            PreparedStatement statementCountry = conn.prepareStatement(sqlCountry);
            statementCountry.setString(1, customerData.getBillingAddressCountry());
            statementCountry.setString(2, customerData.getBillingAddressCountry());
            statementCountry.executeUpdate();


            String sqlZipCode = "INSERT INTO zip_code (code) SELECT (?) " +
                    "WHERE NOT EXISTS (SELECT 1 FROM zip_code WHERE code=(?))";
            PreparedStatement statementZipCode = conn.prepareStatement(sqlZipCode);
            statementZipCode.setString(1, customerData.getBillingAddressZipCode());
            statementZipCode.setString(2, customerData.getBillingAddressZipCode());
            statementZipCode.executeUpdate();


            int country_id = getCountryId(customerData.getBillingAddressCountry());
            int zip_code_id = getZipCodeId(customerData.getBillingAddressZipCode());

            String sqlCity = "INSERT INTO city (city_name, country_id, zip_code_id) SELECT ?, ?, ? " +
                    "WHERE NOT EXISTS (SELECT 1 FROM city WHERE city_name=? AND country_id=? AND zip_code_id=?)";
            PreparedStatement statementCity = conn.prepareStatement(sqlCity);
            statementCity.setString(1, customerData.getBillingAddressCity());
            statementCity.setInt(2, country_id);
            statementCity.setInt(3, zip_code_id);
            statementCity.setString(4, customerData.getBillingAddressCity());
            statementCity.setInt(5, country_id);
            statementCity.setInt(6, zip_code_id);
            statementCity.executeUpdate();


            int city_id = getCityId(country_id, zip_code_id, customerData.getBillingAddressCity());

            String streetSql = "INSERT INTO street (street_name) SELECT (?) " +
                    "WHERE NOT EXISTS (SELECT 1 FROM street WHERE street_name=?)";
            PreparedStatement streetStatement = conn.prepareStatement(streetSql);
            streetStatement.setString(1, customerData.getBillingAddress());
            streetStatement.setString(2, customerData.getBillingAddress());
            streetStatement.executeUpdate();


            int street_id = getStreetId(customerData.getBillingAddress());

            String sqlShippingCountry = "INSERT INTO country (country_name) SELECT (?) " +
                    "WHERE NOT EXISTS (SELECT 1 FROM country WHERE country_name=(?))";
            PreparedStatement statementShippingCountry = conn.prepareStatement(sqlShippingCountry);
            statementShippingCountry.setString(1, customerData.getShippingAddressCountry());
            statementShippingCountry.setString(2, customerData.getShippingAddressCountry());
            statementShippingCountry.executeUpdate();


            String sqlShippingZipCode = "INSERT INTO zip_code (code) SELECT (?) " +
                    "WHERE NOT EXISTS (SELECT 1 FROM zip_code WHERE code=(?))";
            PreparedStatement statementShippingZipCode = conn.prepareStatement(sqlShippingZipCode);
            statementShippingZipCode.setString(1, customerData.getShippingAddressZipCode());
            statementShippingZipCode.setString(2, customerData.getShippingAddressZipCode());
            statementShippingZipCode.executeUpdate();


            int country_id_shipping = getCountryId(customerData.getShippingAddressCountry());
            int zip_code_id_shipping = getZipCodeId(customerData.getShippingAddressZipCode());

            String sqlShippingCity = "INSERT INTO city (city_name, country_id, zip_code_id) SELECT ?, ?, ? " +
                    "WHERE NOT EXISTS (SELECT 1 FROM city WHERE city_name=? AND country_id=? AND zip_code_id=?)";
            PreparedStatement statementShippingCity = conn.prepareStatement(sqlShippingCity);
            statementShippingCity.setString(1, customerData.getShippingAddressCity());
            statementShippingCity.setInt(2, country_id_shipping);
            statementShippingCity.setInt(3, zip_code_id_shipping);
            statementShippingCity.setString(4, customerData.getShippingAddressCity());
            statementShippingCity.setInt(5, country_id_shipping);
            statementShippingCity.setInt(6, zip_code_id_shipping);
            statementShippingCity.executeUpdate();


            int city_id_shipping = getCityId(country_id_shipping, zip_code_id_shipping, customerData.getShippingAddressCity());


            String shippingStreetSql = "INSERT INTO street (street_name) SELECT (?) " +
                    "WHERE NOT EXISTS (SELECT 1 FROM street WHERE street_name=?)";
            PreparedStatement shippingStreetStatement = conn.prepareStatement(shippingStreetSql);
            shippingStreetStatement.setString(1, customerData.getShippingAddress());
            shippingStreetStatement.setString(2, customerData.getShippingAddress());
            shippingStreetStatement.executeUpdate();


            int street_id_shipping = getStreetId(customerData.getShippingAddress());


            String addressSql = "INSERT INTO address (country_id, zip_code_id, street_id, city_id) VALUES (?, ?, ?, ?)";
            PreparedStatement addressStatement = conn.prepareStatement(addressSql);
            addressStatement.setInt(1, country_id);
            addressStatement.setInt(2, zip_code_id);
            addressStatement.setInt(3, street_id);
            addressStatement.setInt(4, city_id);
            addressStatement.executeUpdate();


            int addressId = getAddressId(country_id, zip_code_id, city_id, street_id);
            String shippingAddressSql = "INSERT INTO address (country_id, zip_code_id, street_id, city_id) VALUES (?, ?, ?, ?)";
            PreparedStatement shippingAddressStatement = conn.prepareStatement(shippingAddressSql);
            shippingAddressStatement.setInt(1, country_id_shipping);
            shippingAddressStatement.setInt(2, zip_code_id_shipping);
            shippingAddressStatement.setInt(3, street_id_shipping);
            shippingAddressStatement.setInt(4, city_id_shipping);
            shippingAddressStatement.executeUpdate();

            int addressIdShipping = getAddressId(country_id_shipping, zip_code_id_shipping, city_id_shipping, street_id_shipping);


            String customerDataSql = "INSERT INTO customer_data " +
                    "(user_id, billing_address_id, shipping_address_id, customer_email, " +
                    "customer_name, customer_phone_number, session_id) " +
                    "SELECT ?, ?, ?, ?, ?, ?, ? " +
                    "WHERE NOT EXISTS (SELECT 1 FROM customer_data WHERE session_id=?)";

            PreparedStatement customerDataStatement = conn.prepareStatement(customerDataSql);
            customerDataStatement.setInt(1, customerData.getUserId());
            customerDataStatement.setInt(2, addressId);
            customerDataStatement.setInt(3, addressIdShipping);
            customerDataStatement.setString(4, customerData.getCustomerEmail());
            customerDataStatement.setString(5, customerData.getCustomerName());
            customerDataStatement.setString(6, customerData.getCustomerPhoneNumber());
            customerDataStatement.setString(7, customerData.getName());
            customerDataStatement.setString(8, customerData.getName());
            customerDataStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getStreetId(String street) {
        try(Connection conn = dataSource.getConnection()){
            String sqlCountry = "SELECT id FROM street WHERE street_name=?";
            PreparedStatement statementStreet = conn.prepareStatement(sqlCountry);
            statementStreet.setString(1, street);
            ResultSet streetId = statementStreet.executeQuery();
            streetId.next();
            return streetId.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public CustomerData find(int id) {
        try(Connection conn = dataSource.getConnection()){
            String sqlCountry = "SELECT user_id, session_id, customer_name, customer_phone_number, customer_email, billing_address_id, shipping_address_id " +
                    "FROM customer_data  " +
                    "WHERE user_id=?";
            PreparedStatement statement = conn.prepareStatement(sqlCountry);
            statement.setInt(1, id);
            ResultSet customer_data = statement.executeQuery();
            if(customer_data.next()) {
                CustomerData customer = new CustomerData(customer_data.getString("session_id"));
                customer.setUserId(customer_data.getInt("user_id"));
                return customer;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    private int getCountryId(String countryName){
        try(Connection conn = dataSource.getConnection()){
            String sqlCountry = "SELECT id FROM country WHERE country_name=?";
            PreparedStatement statementCountry = conn.prepareStatement(sqlCountry);
            statementCountry.setString(1, countryName);
            ResultSet countryId = statementCountry.executeQuery();
            countryId.next();
            return countryId.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getZipCodeId(String zipCode) {
        try(Connection conn = dataSource.getConnection()){
            String sqlCountry = "SELECT id FROM zip_code WHERE code=?";
            PreparedStatement statementZipCode = conn.prepareStatement(sqlCountry);
            statementZipCode.setString(1, zipCode);
            ResultSet zipCodeId = statementZipCode.executeQuery();
            zipCodeId.next();
            return zipCodeId.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getCityId(int countryId, int zipCode, String cityName) {
        try(Connection conn = dataSource.getConnection()){
            String sqlCountry = "SELECT id FROM city WHERE country_id=? AND zip_code_id=? AND city_name=?";
            PreparedStatement statementZipCode = conn.prepareStatement(sqlCountry);
            statementZipCode.setInt(1, countryId);
            statementZipCode.setInt(2, zipCode);
            statementZipCode.setString(3, cityName);
            ResultSet cityId = statementZipCode.executeQuery();
            cityId.next();
            return cityId.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getAddressId(int countryId, int zipCodeId, int cityId, int streetId){
        try(Connection conn = dataSource.getConnection()){
            String sqlCountry = "SELECT id FROM address WHERE country_id=? AND zip_code_id=? AND city_id=? AND street_id=?";
            PreparedStatement statementZipCode = conn.prepareStatement(sqlCountry);
            statementZipCode.setInt(1, countryId);
            statementZipCode.setInt(2, zipCodeId);
            statementZipCode.setInt(3, cityId);
            statementZipCode.setInt(4, streetId);
            ResultSet adressId = statementZipCode.executeQuery();
            adressId.next();
            return adressId.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
