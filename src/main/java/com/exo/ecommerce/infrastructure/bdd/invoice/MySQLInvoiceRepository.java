package com.exo.ecommerce.infrastructure.bdd.invoice;

import com.exo.ecommerce.domain.invoice.Invoice;
import com.exo.ecommerce.domain.invoice.InvoiceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLInvoiceRepository implements InvoiceRepository {
    private InvoiceCRUDRepository crudRepository;

    public MySQLInvoiceRepository(InvoiceCRUDRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Invoice save(Invoice invoice) {

        return crudRepository.save(com.exo.ecommerce.infrastructure.bdd.invoice.Invoice.fromDomainEntity(invoice))
                .toDomainEntity();
    }

    @Override
    public List<Invoice> findAll() {
        ArrayList<Invoice> invoices = new ArrayList<Invoice>();
        for (com.exo.ecommerce.infrastructure.bdd.invoice.Invoice invoice : crudRepository.findAll()) {
            invoices.add(invoice.toDomainEntity());
        }
        return invoices;
    }

    @Override
    public Optional<Invoice> findById(long id) {
        return crudRepository.findById(id)
                .map(com.exo.ecommerce.infrastructure.bdd.invoice.Invoice::toDomainEntity);
    }
}
