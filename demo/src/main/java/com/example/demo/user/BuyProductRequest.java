package com.example.demo.user;

public class BuyProductRequest {
    private String username;
    private String password;
    private Long productId;

    public BuyProductRequest() {
    }

    public BuyProductRequest(String username, String password, Long productId) {
        this.username = username;
        this.password = password;
        this.productId = productId;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
