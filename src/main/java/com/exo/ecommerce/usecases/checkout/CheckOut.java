package com.exo.ecommerce.usecases.checkout;

import com.exo.ecommerce.domain.cart.Cart;
import com.exo.ecommerce.domain.cart.CartRepository;
import com.exo.ecommerce.domain.invoice.Invoice;
import com.exo.ecommerce.domain.invoice.InvoiceRepository;

import java.util.Optional;

public class CheckOut {

    private CartRepository cartRepository;
    private InvoiceRepository invoiceRepository;

    public CheckOut(CartRepository cartRepository, InvoiceRepository invoiceRepository) {
        this.cartRepository = cartRepository;
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice handle() throws NothingToCheckOutException {
        Optional<Cart> currentCart = cartRepository.fetchCurrentCart();
        if (currentCart.isPresent() && !currentCart.get().getItems().isEmpty()) {
            Cart cart = currentCart.get();
            Invoice invoice = new Invoice(cart);
            cart.setCheckedOut(true);
            invoice = invoiceRepository.save(invoice);
            cartRepository.save(cart);
            return invoice;
        } else {
            throw new NothingToCheckOutException();
        }
    }
}
