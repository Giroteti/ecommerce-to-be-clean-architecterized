package com.exo.ecommerce;

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
            DecimalFormat numberFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.FRANCE);
            numberFormat.applyPattern("###,###.## ¤");
            DecimalFormatSymbols symbols = numberFormat.getDecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator(' ');
            symbols.setCurrency(Currency.getInstance("EUR"));
            numberFormat.setDecimalFormatSymbols(symbols);
            this.total = numberFormat.format(cart.calculateTotalPrice());
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
