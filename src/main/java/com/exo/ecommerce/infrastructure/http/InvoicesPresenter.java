package com.exo.ecommerce.infrastructure.http;

import com.exo.ecommerce.domain.invoice.Invoice;

import java.util.ArrayList;
import java.util.List;

public class InvoicesPresenter {
    public ArrayList<InvoiceResponse> present(List<Invoice> invoices)
    {
        ArrayList<InvoiceResponse> response = new ArrayList<InvoiceResponse>();
        for (Invoice invoice : invoices) {
            response.add(new InvoiceResponse(invoice));
        }
        return response;
    }
}
