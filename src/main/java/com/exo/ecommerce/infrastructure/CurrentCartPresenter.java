package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.domain.Cart;

public class CurrentCartPresenter {
    public CartResponse present(Cart cart) {
        if (cart != null && !cart.getItems().isEmpty()) {
            return new CartResponse(cart);
        } else {
            return new CartResponse(null);
        }
    }
}
