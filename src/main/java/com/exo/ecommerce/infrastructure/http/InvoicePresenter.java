package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.domain.invoice.Invoice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class InvoicePresenter {
    public ResponseEntity present(Invoice invoice) {
        if (invoice != null) {
            return ResponseEntity.ok(new InvoiceResponse(invoice));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
