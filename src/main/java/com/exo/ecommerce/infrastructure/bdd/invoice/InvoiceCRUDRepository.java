package com.exo.ecommerce.infrastructure.bdd.invoice;

import org.springframework.data.repository.CrudRepository;

public interface InvoiceCRUDRepository extends CrudRepository<Invoice, Long> {

}