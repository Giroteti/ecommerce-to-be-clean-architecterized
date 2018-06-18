package com.exo.ecommerce.domain.cart;

import java.util.Optional;

public interface CartRepository {

    Optional<Cart> fetchCurrentCart();

    Cart save(Cart cart);

}
