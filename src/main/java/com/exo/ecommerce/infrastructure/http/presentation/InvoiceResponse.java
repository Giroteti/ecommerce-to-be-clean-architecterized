package com.exo.ecommerce.infrastructure.http.presentation;

import com.exo.ecommerce.domain.invoice.Invoice;

public class InvoiceResponse {
    private Invoice invoice;
    private String total;

    public InvoiceResponse(Invoice invoice) {
        this.invoice = invoice;
        if (invoice != null) {
            this.total = CurrencyFormatter.formatCurrency(invoice.calculateTotalPrice());
        } else {
            this.total= "";
        }
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public String getTotal() {
        return total;
    }
}
