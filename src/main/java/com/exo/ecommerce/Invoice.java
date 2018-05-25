package com.exo.ecommerce;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String date;
    @OneToOne
    private Cart cart;

    public Invoice() {
    }

    public Invoice(Cart cart) {
        this.cart = cart;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Cart getCart() {
        return cart;
    }

    public float calculateTotalPrice()
    {
        return this.cart.calculateTotalPrice();
    }
}
