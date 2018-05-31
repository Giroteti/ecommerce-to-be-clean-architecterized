package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.domain.Cart;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Currency;
import java.util.Locale;

public class CartResponse {

    private final Cart cart;
    private final String total;

    public CartResponse(Cart cart) {
        this.cart = cart;

        if (cart != null) {
            this.total = CurrencyFormatter.formatCurrency(cart.calculateTotalPrice());
        } else {
            this.total = "";
        }
    }

    public Cart getCart() {
        return cart;
    }

    public String getTotal() {
        return total;
    }
}
