package com.example.demo.user;

public class AddBalanceRequest {
    private String username;
    private String password;
    private double amount;

    // Constructors
    public AddBalanceRequest() {}

    public AddBalanceRequest(String username, String password, double amount) {
        this.username = username;
        this.password = password;
        this.amount = amount;
    }

    // Getters and Setters
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}