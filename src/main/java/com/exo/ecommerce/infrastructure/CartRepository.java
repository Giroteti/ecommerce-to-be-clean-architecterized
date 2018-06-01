package com.exo.ecommerce;

import com.exo.ecommerce.domain.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Long> {
    Optional<Cart> findTopByCheckedOutOrderByIdDesc(Boolean checkedOut);
}