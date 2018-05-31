package com.exo.ecommerce.usecases;

import com.exo.ecommerce.domain.Cart;
import com.exo.ecommerce.domain.CartRepository;

public class GetCurrentCart {
    private CartRepository cartRepository;

    public GetCurrentCart(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart handle() {
        return cartRepository.findTopByCheckedOutOrderByIdDesc(false).orElse(null);
    }
}
