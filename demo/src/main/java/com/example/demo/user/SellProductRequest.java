package com.example.demo.user;

public class SellProductRequest {
    private String username;
    private String password;
    private String productName;
    private String productCategory;
    private String productDescription;
    private double originalPrice;
    private double resellPrice;

    public SellProductRequest() {
    }

    public SellProductRequest(String username, String password, String productName, String productCategory, String productDescription, double originalPrice, double resellPrice) {
        this.username = username;
        this.password = password;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productDescription = productDescription;
        this.originalPrice = originalPrice;
        this.resellPrice = resellPrice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getResellPrice() {
        return resellPrice;
    }

    public void setResellPrice(double resellPrice) {
        this.resellPrice = resellPrice;
    }
}
