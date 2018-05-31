package com.exo.ecommerce.usecases;

import com.exo.ecommerce.domain.Cart;
import com.exo.ecommerce.domain.Item;
import com.exo.ecommerce.infrastructure.CartRepository;
import com.exo.ecommerce.infrastructure.ItemRepository;

import java.util.Optional;

public class AddItemToCart {

    ItemRepository itemRepository;
    CartRepository cartRepository;

    public AddItemToCart(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    public Cart handle(long id) {
        Optional<Item> purchasedItem = itemRepository.findById(id);

        if (purchasedItem.isPresent() && purchasedItem.get().getRemainingInStock() > 0) {
            Item item = purchasedItem.get();
            Optional<Cart> currentCart = cartRepository.findTopByCheckedOutOrderByIdDesc(false);
            Cart cart = null;
            if (currentCart.isPresent()) {
                cart = currentCart.get();
            } else {
                cart = new Cart();
            }
            item.decrementRemainingInStock();
            item = itemRepository.save(item);
            cart.addItem(item);
            cartRepository.save(cart);
            return cart;
        } else {
            return null;
        }
    }
}
