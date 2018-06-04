package com.exo.ecommerce.usecases;

import com.exo.ecommerce.FastTests;
import com.exo.ecommerce.domain.Cart;
import com.exo.ecommerce.domain.CartRepository;
import com.exo.ecommerce.domain.Item;
import com.exo.ecommerce.domain.ItemRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class AddItemCartTests extends TestCase{
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private AddItemToCart underTest;

    @Test
    @Category(FastTests.class)
    public void should_add_available_item_to_cart() {
        // given
        Long itemId = 1L;
        Item returnedItem = new Item(itemId, "Item 1", "Desc 1", 1, (float) 10.0);
        Cart returnedCart = new Cart();

        given(itemRepository.findById(itemId)).willReturn(
                Optional.of(
                        returnedItem
                )
        );
        given(cartRepository.fetchCurrentCart()).willReturn(Optional.of(returnedCart));
        given(itemRepository.save(returnedItem)).willReturn(returnedItem);
        // when
        Cart outputCart = underTest.handle(itemId);

        // then
        ArgumentCaptor<Cart> cartCaptor = ArgumentCaptor.forClass(Cart.class);
        then(cartRepository).should().save(cartCaptor.capture());
        assertEquals(1, cartCaptor.getValue().getItems().size());
        assertEquals(1L, (long) cartCaptor.getValue().getItems().get(0).getId());
        assertEquals(0, (int) returnedItem.getRemainingInStock());
        assertEquals(returnedCart, outputCart);
    }

    @Test
    @Category(FastTests.class)
    public void should_not_add_exhausted_item_to_cart() {
        // given
        Long itemId = 1L;
        Item returnedItem = new Item(itemId, "Item 1", "Desc 1", 0, (float) 10.0);
        given(itemRepository.findById(itemId)).willReturn(
                Optional.of(
                        returnedItem
                )
        );

        // when
        Cart output = underTest.handle(itemId);

        // then
        assertNull(output);
    }

    @Test
    @Category(FastTests.class)
    public void should_not_add_unknown_item_to_cart() {
        // given
        Long itemId = 1L;
        Item returnedItem = new Item(itemId, "Item 1", "Desc 1", 0, (float) 10.0);
        given(itemRepository.findById(itemId)).willReturn(
                Optional.empty()
        );

        // when
        Cart output = underTest.handle(itemId);

        // then
        assertNull(output);
    }
}
