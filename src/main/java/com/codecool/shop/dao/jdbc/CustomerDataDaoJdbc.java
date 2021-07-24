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
        return null;
    }

    @Override
    public void add(CustomerData customerData) {
        try(Connection conn = dataSource.getConnection()){
            String sqlCountry = "INSERT INTO country (country_name) SELECT (?) " +
                    "WHERE NOT EXISTS (SELECT 1 FROM country WHERE country_name=(?)) RETURNING id";
            PreparedStatement statementCountry = conn.prepareStatement(sqlCountry);
            statementCountry.setString(1, customerData.getBillingAddressCountry());
            statementCountry.setString(2, customerData.getBillingAddressCountry());
            ResultSet countryId = statementCountry.executeQuery();
            countryId.next();

            String sqlZipCode = "INSERT INTO zip_code (code) SELECT (?) " +
                    "WHERE NOT EXISTS (SELECT 1 FROM zip_code WHERE code=(?)) RETURNING id";
            PreparedStatement statementZipCode = conn.prepareStatement(sqlZipCode);
            statementZipCode.setString(1, customerData.getBillingAddressZipCode());
            statementZipCode.setString(2, customerData.getBillingAddressZipCode());
            ResultSet zipCodeId = statementZipCode.executeQuery();
            zipCodeId.next();

            String sqlCity = "INSERT INTO city (city_name, country_id, zip_code_id) SELECT (?, ?, ?) " +
                    "WHERE NOT EXISTS (SELECT 1 FROM city WHERE city_name=(?) AND country_id=? AND zip_code_id=?) RETURNING id";
            PreparedStatement statementCity = conn.prepareStatement(sqlCity);
            statementCity.setString(1, customerData.getBillingAddressCity());
            statementCity.setInt(2, countryId.getInt(1));
            statementCity.setInt(3, zipCodeId.getInt(1));
            statementCity.setString(4, customerData.getBillingAddressCity());
            statementCity.setInt(5, countryId.getInt(1));
            statementCity.setInt(6, zipCodeId.getInt(1));
            ResultSet cityId = statementCity.executeQuery();
            cityId.next();

            String streetSql = "INSERT INTO street (street_name) SELECT (?) " +
                    "WHERE NOT EXISTS (SELECT 1 FROM street WHERE street_name=?) RETURNING id";
            PreparedStatement streetStatement = conn.prepareStatement(streetSql);
            streetStatement.setString(1, customerData.getBillingAddress());
            ResultSet streetId = streetStatement.executeQuery();
            streetId.next();

            String sqlShippingCountry = "INSERT INTO country (country_name) SELECT (?) " +
                    "WHERE NOT EXISTS (SELECT 1 FROM country WHERE country_name=(?)) RETURNING id";
            PreparedStatement statementShippingCountry = conn.prepareStatement(sqlShippingCountry);
            statementShippingCountry.setString(1, customerData.getShippingAddressCountry());
            statementShippingCountry.setString(2, customerData.getShippingAddressCountry());
            ResultSet shippingCountryId = statementShippingCountry.executeQuery();
            shippingCountryId.next();

            String sqlShippingZipCode = "INSERT INTO zip_code (code) SELECT (?) " +
                    "WHERE NOT EXISTS (SELECT 1 FROM zip_code WHERE code=(?)) RETURNING id";
            PreparedStatement statementShippingZipCode = conn.prepareStatement(sqlShippingZipCode);
            statementShippingZipCode.setString(1, customerData.getShippingAddressZipCode());
            statementShippingZipCode.setString(2, customerData.getShippingAddressZipCode());
            ResultSet shippingZipCodeId = statementShippingZipCode.executeQuery();
            shippingZipCodeId.next();

            String sqlShippingCity = "INSERT INTO city (city_name, country_id, zip_code_id) SELECT (?, ?, ?) " +
                    "WHERE NOT EXISTS (SELECT 1 FROM city WHERE city_name=(?) AND country_id=? AND zip_code_id=?) RETURNING id";
            PreparedStatement statementShippingCity = conn.prepareStatement(sqlShippingCity);
            statementShippingCity.setString(1, customerData.getShippingAddressCity());
            statementShippingCity.setInt(2, shippingCountryId.getInt(1));
            statementShippingCity.setInt(3, shippingZipCodeId.getInt(1));
            statementShippingCity.setString(4, customerData.getShippingAddressCity());
            statementShippingCity.setInt(5, shippingCountryId.getInt(1));
            statementShippingCity.setInt(6, shippingZipCodeId.getInt(1));
            ResultSet shippingCityId = statementCity.executeQuery();
            shippingCityId.next();

            String shippingStreetSql = "INSERT INTO street (street_name) SELECT (?) " +
                    "WHERE NOT EXISTS (SELECT 1 FROM street WHERE street_name=?) RETURNING id";
            PreparedStatement shippingStreetStatement = conn.prepareStatement(shippingStreetSql);
            shippingStreetStatement.setString(1, customerData.getShippingAddress());
            ResultSet shippingStreetId = shippingStreetStatement.executeQuery();
            shippingStreetId.next();

            String addressSql = "INSERT INTO address (country_id, zip_code_id, street_id, city_id) VALUES (?, ?, ?, ?) RETURNING id";
            PreparedStatement addressStatement = conn.prepareStatement(addressSql);
            addressStatement.setInt(1, countryId.getInt(1));
            addressStatement.setInt(1, zipCodeId.getInt(1));
            addressStatement.setInt(1, streetId.getInt(1));
            addressStatement.setInt(1, cityId.getInt(1));
            ResultSet billingAddress = addressStatement.executeQuery();
            billingAddress.next();

            String shippingAddressSql = "INSERT INTO address (country_id, zip_code_id, street_id, city_id) VALUES (?, ?, ?, ?) RETURNING id";
            PreparedStatement shippingAddressStatement = conn.prepareStatement(shippingAddressSql);
            shippingAddressStatement.setInt(1, shippingCountryId.getInt(1));
            shippingAddressStatement.setInt(1, shippingZipCodeId.getInt(1));
            shippingAddressStatement.setInt(1, shippingStreetId.getInt(1));
            shippingAddressStatement.setInt(1, shippingCityId.getInt(1));
            ResultSet shippingAddress = shippingAddressStatement.executeQuery();
            shippingAddress.next();

            String customerDataSql = "INSERT INTO customer_data " +
                    "(user_id, billing_address_id, shipping_address_id, customer_email, " +
                    "customer_name, customer_phone_number) " +
                    "SELECT (?, ?, ?, ?, ?, ?) " +
                    "WHERE NOT EXISTS(SELECT 1 FROM customer_data WHERE user_id=(?))";

            PreparedStatement customerDataStatement = conn.prepareStatement(customerDataSql);
            customerDataStatement.setInt(1, customerData.getId());
            customerDataStatement.setInt(2, billingAddress.getInt(1));
            customerDataStatement.setInt(3, shippingAddress.getInt(1));
            customerDataStatement.setString(4, customerData.getCustomerEmail());
            customerDataStatement.setString(5, customerData.getCustomerName());
            customerDataStatement.setString(6, customerData.getCustomerPhoneNumber());
            customerDataStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public CustomerData find(int id) {
        return null;
    }
}
