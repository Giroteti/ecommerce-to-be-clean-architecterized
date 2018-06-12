package com.exo.ecommerce.usecases.getcurrentcart;

import com.exo.ecommerce.domain.cart.Cart;
import com.exo.ecommerce.domain.cart.CartRepository;

public class GetCurrentCart {
    private CartRepository cartRepository;

    public GetCurrentCart(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart handle() throws NoCurrentCartException {
        Cart cart = cartRepository.fetchCurrentCart().orElse(null);
        if (cart == null) {
            throw new NoCurrentCartException();
        }
        return cart;
    }
}
