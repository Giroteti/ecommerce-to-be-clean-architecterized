package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.domain.cart.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartCRUDRepository extends CrudRepository<Cart, Long> {
    Optional<Cart> findTopByCheckedOutOrderByIdDesc(Boolean checkedOut);
}