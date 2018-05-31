package com.exo.ecommerce.domain;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository {
    public Invoice save(Invoice invoice);
    public List<Invoice> findAll();
    public Optional<Invoice> findById(long id);
}
