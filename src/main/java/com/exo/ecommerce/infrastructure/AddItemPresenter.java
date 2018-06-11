package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.domain.cart.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AddItemPresenter {

    public ResponseEntity present(Cart cart, long itemId) {
        if (cart != null) {
            return ResponseEntity.ok(new AddItemResponse("Item " + itemId + " added successfully to the cart", cart));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
