package com.exo.ecommerce.infrastructure.http.presentation.getcurrentcart;

import com.exo.ecommerce.domain.cart.Cart;
import com.exo.ecommerce.infrastructure.http.presentation.CartResponse;
import org.springframework.stereotype.Component;

@Component
public class CurrentCartPresenter {
    public CartResponse present(Cart cart) {
        if (cart.getItems().isEmpty()) {
            return new CartResponse(null);
        }
        return new CartResponse(cart);
    }
}
