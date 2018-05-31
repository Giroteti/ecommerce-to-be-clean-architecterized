package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.domain.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

}