package com.exo.ecommerce.usecases;

import com.exo.ecommerce.domain.invoice.Invoice;
import com.exo.ecommerce.domain.invoice.InvoiceRepository;

public class GetInvoice {
    private InvoiceRepository invoiceRepository;

    public GetInvoice(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice handle(long invoiceId)
    {
        return invoiceRepository.findById(invoiceId).orElse(null);
    }
}
