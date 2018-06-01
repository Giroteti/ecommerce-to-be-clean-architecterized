package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.domain.Cart;
import com.exo.ecommerce.domain.CartRepository;

import java.util.Optional;

public class CartMySQLRepository implements CartRepository {

    private CartCRUDRepository crudRepository;

    public CartMySQLRepository(CartCRUDRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Optional<Cart> findTopByCheckedOutOrderByIdDesc(boolean isCheckedOut) {
        return this.crudRepository.findTopByCheckedOutOrderByIdDesc(isCheckedOut);
    }

    @Override
    public Cart save(Cart cart) {
        return this.crudRepository.save(cart);
    }
}
