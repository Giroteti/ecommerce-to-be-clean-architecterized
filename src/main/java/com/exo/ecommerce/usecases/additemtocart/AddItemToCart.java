package com.exo.ecommerce.usecases.additemtocart;

import com.exo.ecommerce.domain.cart.Cart;
import com.exo.ecommerce.domain.cart.CartRepository;
import com.exo.ecommerce.domain.item.Item;
import com.exo.ecommerce.domain.item.ItemRepository;

import java.util.Optional;

public class AddItemToCart {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public AddItemToCart(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    public Cart handle(long id) throws UnknownItemException, UnavailableItemExeption {
        Optional<Item> purchasedItem = itemRepository.findById(id);
        if (!purchasedItem.isPresent()) {
            throw new UnknownItemException();
        }
        if (purchasedItem.get().getRemainingInStock() <= 0) {
            throw new UnavailableItemExeption();
        }
        return purchaseItem(purchasedItem.get());
    }

    private Cart purchaseItem(Item purchasedItem) {
        Item item = purchasedItem;
        Optional<Cart> currentCart = cartRepository.fetchCurrentCart();
        Cart cart = currentCart.orElseGet(Cart::new);
        item.decrementRemainingInStock();
        item = itemRepository.save(item);
        cart.addItem(item);
        cartRepository.save(cart);
        return cart;
    }
}
