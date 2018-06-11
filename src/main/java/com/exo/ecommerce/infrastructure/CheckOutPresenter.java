package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.domain.invoice.Invoice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CheckOutPresenter {

    public ResponseEntity present(Invoice invoice)
    {
        if (invoice != null) {
            return ResponseEntity.ok(new CheckOutCartResponse("Cart checked out successfully", invoice));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
