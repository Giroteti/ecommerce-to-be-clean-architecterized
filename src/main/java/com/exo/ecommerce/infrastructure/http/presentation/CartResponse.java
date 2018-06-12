package com.exo.ecommerce.infrastructure.http.presentation;

import com.exo.ecommerce.domain.cart.Cart;

public class CartResponse {

    private final Cart cart;
    private final String total;

    public CartResponse(Cart cart) {
        this.cart = cart;

        if (cart != null) {
            this.total = CurrencyFormatter.formatCurrency(cart.calculateTotalPrice());
        } else {
            this.total = "";
        }
    }

    public Cart getCart() {
        return cart;
    }

    public String getTotal() {
        return total;
    }
}
