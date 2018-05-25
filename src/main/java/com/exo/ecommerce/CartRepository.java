package com.exo.ecommerce;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Long> {
    Optional<Cart> findTopByCheckedOutOrderByIdDesc(Boolean checkedOut);
}