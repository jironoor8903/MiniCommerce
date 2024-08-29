package com.example.demo.product;

public class ProductRequest {
    private String name;
    private String category;
    private String description;
    private double originalPrice;
    private double resellPrice;

    public ProductRequest() {
    }

    public ProductRequest(String name, String category, String description, double originalPrice, double resellPrice) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.originalPrice = originalPrice;
        this.resellPrice = resellPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
