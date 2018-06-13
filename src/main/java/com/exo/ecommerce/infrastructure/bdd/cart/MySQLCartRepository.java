package com.exo.ecommerce.infrastructure.bdd.cart;

import com.exo.ecommerce.domain.cart.Cart;
import com.exo.ecommerce.domain.cart.CartRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MySQLCartRepository implements CartRepository {

    private CartCRUDRepository crudRepository;

    public MySQLCartRepository(CartCRUDRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Optional<Cart> fetchCurrentCart() {
        return this.crudRepository.findTopByCheckedOutOrderByIdDesc(false)
                .map(com.exo.ecommerce.infrastructure.bdd.cart.Cart::toDomainEntity);
    }

    @Override
    public Cart save(Cart cart) {
        return this.crudRepository.save(
                com.exo.ecommerce.infrastructure.bdd.cart.Cart.fromDomainEntity(cart)
        ).toDomainEntity();
    }
}
