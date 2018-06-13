package com.exo.ecommerce.domain.invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository {
    Invoice save(Invoice invoice);
    List<Invoice> findAll();
    Optional<Invoice> findById(long id);
}
