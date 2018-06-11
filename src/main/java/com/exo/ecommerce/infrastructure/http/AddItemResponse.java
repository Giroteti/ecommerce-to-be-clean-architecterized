package com.exo.ecommerce.infrastructure.http;

import com.exo.ecommerce.domain.cart.Cart;

public class AddItemResponse {
    private CartResponse cart;
    public String message;

    public AddItemResponse(String message) {
        this(message, null);
    }

    public AddItemResponse(String message, Cart cart) {
        this.message = message;
        this.cart = new CartResponse(cart);
    }

    public String getMessage() {
        return message;
    }

    public CartResponse getCart() {
        return cart;
    }
}
