package com.exo.ecommerce.infrastructure.http.presentation.getallinvoices;

import com.exo.ecommerce.domain.invoice.Invoice;
import com.exo.ecommerce.infrastructure.http.presentation.InvoiceResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
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
