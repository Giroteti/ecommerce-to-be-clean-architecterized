package com.exo.ecommerce.infrastructure.http.routing;

import com.exo.ecommerce.infrastructure.http.presentation.InvoiceResponse;
import com.exo.ecommerce.infrastructure.http.presentation.getallinvoices.InvoicesPresenter;
import com.exo.ecommerce.infrastructure.http.presentation.getinvoice.InvoicePresenter;
import com.exo.ecommerce.usecases.getallinvoices.GetAllInvoices;
import com.exo.ecommerce.usecases.getinvoice.GetInvoice;
import com.exo.ecommerce.usecases.getinvoice.InvoiceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class InvoiceController {

    private final GetAllInvoices getAllInvoices;
    private final InvoicesPresenter invoicesPresenter;

    private final GetInvoice getInvoice;
    private final InvoicePresenter invoicePresenter;

    public InvoiceController(GetAllInvoices getAllInvoices, InvoicesPresenter invoicesPresenter, GetInvoice getInvoice, InvoicePresenter invoicePresenter) {
        this.getAllInvoices = getAllInvoices;
        this.invoicesPresenter = invoicesPresenter;
        this.getInvoice = getInvoice;
        this.invoicePresenter = invoicePresenter;
    }

    @GetMapping(path = "/invoices", produces = APPLICATION_JSON_VALUE)
    public List<InvoiceResponse> allInvoices() {
        return invoicesPresenter.present(getAllInvoices.handle());
    }

    @GetMapping(path = "/invoice", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity invoice(@RequestParam("id") Long invoiceId) throws InvoiceNotFoundException {
        return invoicePresenter.present(getInvoice.handle(invoiceId));
    }
}
