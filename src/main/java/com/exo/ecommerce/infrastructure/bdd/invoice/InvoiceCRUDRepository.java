package com.exo.ecommerce.infrastructure.bdd.invoice;

import com.exo.ecommerce.domain.invoice.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceCRUDRepository extends CrudRepository<Invoice, Long> {

}