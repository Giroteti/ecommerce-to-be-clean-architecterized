package com.exo.ecommerce.infrastructure.http.presentation.additemtocart;

import com.exo.ecommerce.domain.cart.Cart;
import com.exo.ecommerce.infrastructure.http.presentation.CartResponse;

public class ItemAddedToCartResponse {
    private CartResponse cart;
    public String message;

    public ItemAddedToCartResponse(long itemId, Cart cart) {
        this.message = "Item " + itemId + " added successfully to the cart";
        this.cart = new CartResponse(cart);
    }

    public String getMessage() {
        return message;
    }

    public CartResponse getCart() {
        return cart;
    }
}
