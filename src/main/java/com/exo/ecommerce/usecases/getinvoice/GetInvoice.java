package com.exo.ecommerce.usecases.getinvoice;

import com.exo.ecommerce.domain.invoice.Invoice;
import com.exo.ecommerce.domain.invoice.InvoiceRepository;

public class GetInvoice {
    private InvoiceRepository invoiceRepository;

    public GetInvoice(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice handle(long invoiceId) throws InvoiceNotFoundException {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElse(null);
        if (invoice == null) {
            throw new InvoiceNotFoundException();
        }
        return invoice;
    }
}
