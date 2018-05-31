package com.exo.ecommerce.usecases;

import com.exo.ecommerce.domain.Cart;
import com.exo.ecommerce.domain.CartRepository;
import com.exo.ecommerce.domain.Invoice;
import com.exo.ecommerce.domain.InvoiceRepository;

import java.util.Optional;

public class CheckOut {

    private CartRepository cartRepository;
    private InvoiceRepository invoiceRepository;

    public CheckOut(CartRepository cartRepository, InvoiceRepository invoiceRepository) {
        this.cartRepository = cartRepository;
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice handle()
    {
        Optional<Cart> currentCart = cartRepository.findTopByCheckedOutOrderByIdDesc(false);
        if (currentCart.isPresent() && !currentCart.get().getItems().isEmpty()) {
            Cart cart = currentCart.get();
            Invoice invoice = new Invoice(cart);
            cart.setCheckedOut(true);
            invoiceRepository.save(invoice);
            cartRepository.save(cart);
            return invoice;
        } else {
            return null;
        }
    }
}
