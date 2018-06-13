package com.exo.ecommerce.infrastructure.http.presentation.getallinvoices;

import com.exo.ecommerce.domain.invoice.Invoice;
import com.exo.ecommerce.infrastructure.http.presentation.InvoiceResponse;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class InvoicesPresenter {
    public List<InvoiceResponse> present(List<Invoice> invoices) {
        return invoices.stream()
                .map(InvoiceResponse::new)
                .collect(toList());
    }
}
