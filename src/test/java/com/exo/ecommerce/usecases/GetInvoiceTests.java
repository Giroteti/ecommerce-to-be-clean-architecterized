package com.exo.ecommerce.usecases;

import com.exo.ecommerce.FastTests;
import com.exo.ecommerce.domain.invoice.Invoice;
import com.exo.ecommerce.domain.invoice.InvoiceRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GetInvoiceTests extends TestCase {
    @Mock
    private InvoiceRepository invoiceRepository;
    @InjectMocks
    private GetInvoice underTest;

    @Test
    @Category(FastTests.class)
    public void should_return_invoices_when_available() {
        // given
        Invoice returnedInvoice = new Invoice();
        given(invoiceRepository.findById(1L)).willReturn(Optional.of(returnedInvoice));

        // when
        Invoice output = underTest.handle(1L);

        // then
        assertEquals(returnedInvoice, output);
    }

    @Test
    @Category(FastTests.class)
    public void should_return_null_when_no_invoice_available() {
        // when
        Invoice output = underTest.handle(1L);

        // then
        assertNull(output);
    }
}
