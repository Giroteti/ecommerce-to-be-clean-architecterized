package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.domain.Invoice;

public class CheckOutCartResponse {
    private String message;
    private Invoice invoice;

    public CheckOutCartResponse(String message) {
        this(message, null);
    }

    public CheckOutCartResponse(String message, Invoice invoice) {
        this.message = message;
        this.invoice = invoice;
    }

    public String getMessage() {
        return message;
    }

    public Invoice getInvoice() {
        return invoice;
    }
}
