package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.domain.Invoice;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Currency;
import java.util.Locale;

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
