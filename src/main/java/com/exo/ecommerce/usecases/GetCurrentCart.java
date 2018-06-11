package com.exo.ecommerce.usecases;

import com.exo.ecommerce.domain.cart.Cart;
import com.exo.ecommerce.domain.cart.CartRepository;

public class GetCurrentCart {
    private CartRepository cartRepository;

    public GetCurrentCart(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart handle() {
        return cartRepository.fetchCurrentCart().orElse(null);
    }
}
