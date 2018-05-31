package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.domain.Invoice;
import com.exo.ecommerce.domain.InvoiceRepository;

public class MySQLInvoiceRepository implements InvoiceRepository {
    private InvoiceCRUDRepository crudRepository;

    public MySQLInvoiceRepository(InvoiceCRUDRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Invoice save(Invoice invoice) {
        return crudRepository.save(invoice);
    }
}
