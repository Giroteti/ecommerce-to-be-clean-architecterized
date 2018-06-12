package com.exo.ecommerce.infrastructure.http.presentation.getinvoice;

import com.exo.ecommerce.domain.invoice.Invoice;
import com.exo.ecommerce.infrastructure.http.presentation.InvoiceResponse;
import org.springframework.http.ResponseEntity;

public class InvoicePresenter {
    public ResponseEntity present(Invoice invoice) {
        return ResponseEntity.ok(new InvoiceResponse(invoice));
    }
}
