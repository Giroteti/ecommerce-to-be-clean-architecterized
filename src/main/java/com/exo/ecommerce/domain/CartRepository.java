package com.exo.ecommerce.domain;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findTopByCheckedOutOrderByIdDesc(boolean isCheckedOut);

    Cart save(Cart cart);
}
