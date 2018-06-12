package com.exo.ecommerce.usecases;

import com.exo.ecommerce.FastTests;
import com.exo.ecommerce.domain.cart.Cart;
import com.exo.ecommerce.domain.cart.CartRepository;
import com.exo.ecommerce.usecases.getcurrentcart.GetCurrentCart;
import com.exo.ecommerce.usecases.getcurrentcart.NoCurrentCartException;
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
public class GetCurrentCartTests extends TestCase {
    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private GetCurrentCart underTest;

    @Test
    @Category(FastTests.class)
    public void should_return_cart_when_available() throws NoCurrentCartException {
        // given
        Cart returnedCart = new Cart();
        given(
                cartRepository.fetchCurrentCart()
        ).willReturn(Optional.of(returnedCart));

        // when
        Cart output = underTest.handle();

        // then
        assertEquals(returnedCart, output);
    }

    @Test(expected = NoCurrentCartException.class)
    @Category(FastTests.class)
    public void should_return_null_no_cart() throws NoCurrentCartException {
        given(
                cartRepository.fetchCurrentCart()
        ).willReturn(Optional.empty());

        // when
        Cart output = underTest.handle();
    }
}
