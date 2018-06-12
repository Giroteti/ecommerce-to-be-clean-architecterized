package com.exo.ecommerce.infrastructure.http.presentation.checkout;

import com.exo.ecommerce.domain.invoice.Invoice;

public class CartCheckedOutResponse {
    private String message;
    private Invoice invoice;

    public CartCheckedOutResponse(Invoice invoice) {
        this.message = "Cart checked out successfully";
        this.invoice = invoice;
    }

    public String getMessage() {
        return message;
    }

    public Invoice getInvoice() {
        return invoice;
    }
}
