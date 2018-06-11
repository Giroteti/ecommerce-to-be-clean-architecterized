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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GetAllInvoicesTests extends TestCase {
    @Mock
    private InvoiceRepository invoiceRepository;
    @InjectMocks
    private GetAllInvoices underTest;

    @Test
    @Category(FastTests.class)
    public void should_return_all_invoices()
    {
        ArrayList<Invoice> returnedList = new ArrayList<Invoice>();
        given(invoiceRepository.findAll()).willReturn(returnedList);
        // when
        List<Invoice> output = underTest.handle();

        // then
        assertEquals(returnedList, output);
    }

}
