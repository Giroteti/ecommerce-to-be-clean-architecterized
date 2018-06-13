package com.exo.ecommerce.usecases;

import com.exo.ecommerce.FastTests;
import com.exo.ecommerce.domain.invoice.Invoice;
import com.exo.ecommerce.domain.invoice.InvoiceRepository;
import com.exo.ecommerce.usecases.getinvoice.GetInvoice;
import com.exo.ecommerce.usecases.getinvoice.InvoiceNotFoundException;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GetInvoiceTests {
    @Mock
    private InvoiceRepository invoiceRepository;
    @InjectMocks
    private GetInvoice underTest;

    @Test
    @Category(FastTests.class)
    public void should_return_invoices_when_available() throws InvoiceNotFoundException {
        // given
        Invoice returnedInvoice = new Invoice();
        given(invoiceRepository.findById(1L)).willReturn(Optional.of(returnedInvoice));

        // when
        Invoice output = underTest.handle(1L);

        // then
        assertThat(output).isEqualTo(returnedInvoice);
    }

    @Test(expected = InvoiceNotFoundException.class)
    @Category(FastTests.class)
    public void should_throw_exception_when_no_invoice_available() throws InvoiceNotFoundException {
        // when
        underTest.handle(1L);
    }
}
