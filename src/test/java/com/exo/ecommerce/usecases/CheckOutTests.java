package com.exo.ecommerce.usecases;

import com.exo.ecommerce.FastTests;
import com.exo.ecommerce.domain.cart.Cart;
import com.exo.ecommerce.domain.cart.CartRepository;
import com.exo.ecommerce.domain.invoice.Invoice;
import com.exo.ecommerce.domain.invoice.InvoiceRepository;
import com.exo.ecommerce.domain.item.Item;
import com.exo.ecommerce.usecases.checkout.CheckOut;
import com.exo.ecommerce.usecases.checkout.NothingToCheckOutException;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class CheckOutTests extends TestCase {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private InvoiceRepository invoiceRepository;
    @InjectMocks
    private CheckOut underTest;

    @Test
    @Category(FastTests.class)
    public void should_check_out_existing_non_empty_cart() throws NothingToCheckOutException {
        // given
        Cart returnedCart = new Cart();
        Item itemToCheckOut = new Item(1L, "Item 1", "Desc 1", 1, (float) 10.0);
        returnedCart.addItem(itemToCheckOut);
        returnedCart.addItem(itemToCheckOut);
        given(cartRepository.fetchCurrentCart()).willReturn(Optional.of(returnedCart));
        Invoice returnedInvoice = new Invoice();
        given(invoiceRepository.save(any(Invoice.class))).willReturn(returnedInvoice);

        // when
        Invoice output = underTest.handle();

        // then
        ArgumentCaptor<Invoice> invoiceCaptor = ArgumentCaptor.forClass(Invoice.class);
        then(invoiceRepository).should().save(invoiceCaptor.capture());
        assertEquals((float) 20.0, invoiceCaptor.getValue().calculateTotalPrice());
        assertEquals(returnedCart, invoiceCaptor.getValue().getCart());
        assertNotNull(invoiceCaptor.getValue().getDate());
        then(cartRepository).should().save(returnedCart);
        assertTrue(returnedCart.getCheckedOut());
        assertEquals(returnedInvoice, output);
    }

    @Test(expected = NothingToCheckOutException.class)
    @Category(FastTests.class)
    public void should_not_check_out_empty_cart() throws NothingToCheckOutException {
        // given
        Cart returnedCart = new Cart();
        given(cartRepository.fetchCurrentCart()).willReturn(Optional.of(returnedCart));


        // when
        underTest.handle();
    }

    @Test(expected = NothingToCheckOutException.class)
    @Category(FastTests.class)
    public void should_not_check_out_uninitialized_cart() throws NothingToCheckOutException {
        // given
        given(cartRepository.fetchCurrentCart()).willReturn(Optional.empty());

        // when
        Invoice output = underTest.handle();
    }
}
