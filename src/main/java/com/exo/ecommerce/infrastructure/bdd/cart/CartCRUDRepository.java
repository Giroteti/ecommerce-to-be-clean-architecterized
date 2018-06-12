package com.exo.ecommerce.infrastructure.bdd.cart;

import com.exo.ecommerce.domain.cart.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CartCRUDRepository extends CrudRepository<Cart, Long> {
    Optional<Cart> findTopByCheckedOutOrderByIdDesc(Boolean checkedOut);
}