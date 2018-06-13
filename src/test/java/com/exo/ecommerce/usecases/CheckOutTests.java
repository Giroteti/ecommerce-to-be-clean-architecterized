package com.exo.ecommerce.usecases;

import com.exo.ecommerce.FastTests;
import com.exo.ecommerce.domain.cart.Cart;
import com.exo.ecommerce.domain.cart.CartRepository;
import com.exo.ecommerce.domain.invoice.Invoice;
import com.exo.ecommerce.domain.invoice.InvoiceRepository;
import com.exo.ecommerce.domain.item.Item;
import com.exo.ecommerce.usecases.checkout.CheckOut;
import com.exo.ecommerce.usecases.checkout.NothingToCheckOutException;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class CheckOutTests {
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
        Item itemToCheckOut = new Item(1L, "Item 1", "Desc 1", 1, 10d);
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
        assertThat(invoiceCaptor.getValue().calculateTotalPrice()).isEqualTo(20d);
        assertThat(invoiceCaptor.getValue().getCart()).isEqualTo(returnedCart);
        assertThat(invoiceCaptor.getValue().getDate()).isNotNull();
        then(cartRepository).should().save(returnedCart);
        assertThat(returnedCart.getCheckedOut()).isTrue();
        assertThat(output).isEqualTo(returnedInvoice);
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
        underTest.handle();
    }
}
