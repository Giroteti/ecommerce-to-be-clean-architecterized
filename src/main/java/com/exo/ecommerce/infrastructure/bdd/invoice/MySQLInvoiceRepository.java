package com.exo.ecommerce.infrastructure.bdd.invoice;

import com.exo.ecommerce.domain.invoice.Invoice;
import com.exo.ecommerce.domain.invoice.InvoiceRepository;

import java.util.List;
import java.util.Optional;

public class MySQLInvoiceRepository implements InvoiceRepository {
    private InvoiceCRUDRepository crudRepository;

    public MySQLInvoiceRepository(InvoiceCRUDRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Invoice save(Invoice invoice) {
        return crudRepository.save(invoice);
    }

    @Override
    public List<Invoice> findAll() {
        return (List<Invoice>) crudRepository.findAll();
    }

    @Override
    public Optional<Invoice> findById(long id) {
        return crudRepository.findById(id);
    }
}