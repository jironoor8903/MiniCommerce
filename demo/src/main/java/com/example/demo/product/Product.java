package com.example.demo.product;

import jakarta.persistence.*;
import com.example.demo.user.User;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;

    private String name;
    private String category;
    private String description;
    private double originalPrice;
    private double resellPrice;

    @ManyToOne
    @JoinColumn(name = "bought_by_user_id")
    private User boughtByUser;

    @ManyToOne
    @JoinColumn(name = "sold_by_user_id")
    private User soldByUser;

    // Constructors
    public Product() {
    }

    public Product(String name, String category, String description, double originalPrice, double resellPrice, User soldByUser, User boughtByUser) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.originalPrice = originalPrice;
        this.resellPrice = resellPrice;
        this.soldByUser = soldByUser;
        this.boughtByUser = boughtByUser;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getBoughtByUser() {
        return boughtByUser;
    }

    public void setBoughtByUser(User boughtByUser) {
        this.boughtByUser = boughtByUser;
    }

    public User getSoldByUser() {
        return soldByUser;
    }

    public void setSoldByUser(User soldByUser) {
        this.soldByUser = soldByUser;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", originalPrice=" + originalPrice +
                ", resellPrice=" + resellPrice +
                ", soldByUser=" + soldByUser +
                ", boughtByUser=" + boughtByUser +
                '}';
    }
}
