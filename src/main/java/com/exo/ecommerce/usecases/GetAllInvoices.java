package com.exo.ecommerce.usecases;

import com.exo.ecommerce.domain.Invoice;
import com.exo.ecommerce.domain.InvoiceRepository;

import java.util.List;

public class GetAllInvoices {
    private InvoiceRepository invoiceRepository;

    public GetAllInvoices(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> handle()
    {
        return invoiceRepository.findAll();
    }
}
