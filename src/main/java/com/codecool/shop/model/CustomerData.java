package com.codecool.shop.model;

public class CustomerData extends BaseModel {
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;
    private String billingAddress;
    private String billingAddressCountry;
    private String billingAddressCity;
    private String billingAddressZipCode;
    private String shippingAddress;
    private String shippingAddressCountry;
    private String shippingAddressCity;
    private String shippingAddressZipCode;

    public CustomerData(String id, String customerName, String customerEmail, String customerPhoneNumber,
                        String billingAddress, String billingAddressCountry, String billingAddressCity,
                        String billingAddressZipCode, String shippingAddress, String shippingAddressCountry,
                        String shippingAddressCity, String shippingAddressZipCode) {
        super(id);
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhoneNumber = customerPhoneNumber;
        this.billingAddress = billingAddress;
        this.billingAddressCountry = billingAddressCountry;
        this.billingAddressCity = billingAddressCity;
        this.billingAddressZipCode = billingAddressZipCode;
        this.shippingAddress = shippingAddress;
        this.shippingAddressCountry = shippingAddressCountry;
        this.shippingAddressCity = shippingAddressCity;
        this.shippingAddressZipCode = shippingAddressZipCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingAddressCountry() {
        return billingAddressCountry;
    }

    public void setBillingAddressCountry(String billingAddressCountry) {
        this.billingAddressCountry = billingAddressCountry;
    }

    public String getBillingAddressCity() {
        return billingAddressCity;
    }

    public void setBillingAddressCity(String billingAddressCity) {
        this.billingAddressCity = billingAddressCity;
    }

    public String getBillingAddressZipCode() {
        return billingAddressZipCode;
    }

    public void setBillingAddressZipCode(String billingAddressZipCode) {
        this.billingAddressZipCode = billingAddressZipCode;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingAddressCountry() {
        return shippingAddressCountry;
    }

    public void setShippingAddressCountry(String shippingAddressCountry) {
        this.shippingAddressCountry = shippingAddressCountry;
    }

    public String getShippingAddressCity() {
        return shippingAddressCity;
    }

    public void setShippingAddressCity(String shippingAddressCity) {
        this.shippingAddressCity = shippingAddressCity;
    }

    public String getShippingAddressZipCode() {
        return shippingAddressZipCode;
    }

    public void setShippingAddressZipCode(String shippingAddressZipCode) {
        this.shippingAddressZipCode = shippingAddressZipCode;
    }
}
