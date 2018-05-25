package com.exo.ecommerce;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class InvoiceResponse {
    private Invoice invoice;
    private String total;

    public InvoiceResponse(Invoice invoice) {
        this.invoice = invoice;
        if (invoice != null) {
            DecimalFormat numberFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.FRANCE);
            numberFormat.applyPattern("###,###.## ¤");
            DecimalFormatSymbols symbols = numberFormat.getDecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator(' ');
            symbols.setCurrency(Currency.getInstance("EUR"));
            numberFormat.setDecimalFormatSymbols(symbols);
            this.total = numberFormat.format(invoice.calculateTotalPrice());
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
