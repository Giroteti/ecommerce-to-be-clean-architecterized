package com.exo.ecommerce.domain;

import java.util.Optional;

public interface CartRepository {
    public Optional<Cart> findTopByCheckedOutOrderByIdDesc(boolean isCheckedOut);
    public Cart save(Cart cart);
}
