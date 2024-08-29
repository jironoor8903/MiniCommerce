package com.example.demo.user;

import com.example.demo.product.Product;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User {
    public User(String username, String passwordHash, String email, double storeCredit, double balance) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.storeCredit = storeCredit;
        this.balance = balance;
    }

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String username;
    private String passwordHash;
    private String email;
    private double storeCredit;
    private double balance;

    @OneToMany(mappedBy = "boughtByUser", cascade = CascadeType.ALL)
    private List<Product> productsBought;


    public User() {
    }

    public User(Long id, String username, String passwordHash, String email, double storeCredit, double balance) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.storeCredit = storeCredit;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public double getStoreCredit() {
        return storeCredit;
    }

    public void setStoreCredit(double storeCredit) {
        this.storeCredit = storeCredit;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
