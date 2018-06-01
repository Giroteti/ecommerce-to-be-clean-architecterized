package com.exo.ecommerce.usecases;

import com.exo.ecommerce.domain.Cart;
import com.exo.ecommerce.domain.CartRepository;
import com.exo.ecommerce.domain.Item;
import com.exo.ecommerce.domain.ItemRepository;

import java.util.Optional;

public class AddItemToCart {
    private ItemRepository itemRepository;
    private CartRepository cartRepository;

    public AddItemToCart(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    public Cart handle(long itemId)
    {
        Optional<Item> purchasedItem = itemRepository.findById(itemId);

        if (purchasedItem.isPresent() && purchasedItem.get().getRemainingInStock() > 0) {
            Item item = purchasedItem.get();
            Optional<Cart> currentCart = cartRepository.findTopByCheckedOutOrderByIdDesc(false);
            Cart cart = currentCart.orElseGet(Cart::new);
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
