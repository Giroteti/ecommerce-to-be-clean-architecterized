package com.exo.ecommerce.domain.invoice;

import com.exo.ecommerce.domain.cart.Cart;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Invoice {
    private Long id;
    private String date;
    private Cart cart;

    public Invoice() {
    }

    public Invoice(Cart cart) {
        this.cart = cart;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Invoice(Long id, String date, Cart cart) {
        this.id = id;
        this.date = date;
        this.cart = cart;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
