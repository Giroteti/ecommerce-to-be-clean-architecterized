package com.exo.ecommerce.usecases;

import com.exo.ecommerce.FastTests;
import com.exo.ecommerce.domain.cart.Cart;
import com.exo.ecommerce.domain.cart.CartRepository;
import com.exo.ecommerce.domain.item.Item;
import com.exo.ecommerce.domain.item.ItemRepository;
import com.exo.ecommerce.usecases.additemtocart.AddItemToCart;
import com.exo.ecommerce.usecases.additemtocart.UnavailableItemExeption;
import com.exo.ecommerce.usecases.additemtocart.UnknownItemException;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class AddItemCartTests {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private AddItemToCart underTest;

    @Test
    @Category(FastTests.class)
    public void should_add_available_item_to_cart() throws UnavailableItemExeption, UnknownItemException {
        // given
        Long itemId = 1L;
        Item returnedItem = new Item(itemId, "Item 1", "Desc 1", 1, 10d);
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
        assertThat(cartCaptor.getValue().getItems().size()).isEqualTo(1);
        assertThat((long) cartCaptor.getValue().getItems().get(0).getId()).isEqualTo(1L);
        assertThat((int) returnedItem.getRemainingInStock()).isEqualTo(0);
        assertThat(outputCart).isEqualTo(returnedCart);
    }

    @Test(expected = UnavailableItemExeption.class)
    @Category(FastTests.class)
    public void should_not_add_exhausted_item_to_cart() throws UnavailableItemExeption, UnknownItemException {
        // given
        Long itemId = 1L;
        Item returnedItem = new Item(itemId, "Item 1", "Desc 1", 0, 10d);
        given(itemRepository.findById(itemId)).willReturn(
                Optional.of(
                        returnedItem
                )
        );

        // when
        underTest.handle(itemId);
    }

    @Test(expected = UnknownItemException.class)
    @Category(FastTests.class)
    public void should_not_add_unknown_item_to_cart() throws UnavailableItemExeption, UnknownItemException {
        // given
        Long itemId = 1L;
        given(itemRepository.findById(itemId)).willReturn(
                Optional.empty()
        );

        // when
        Cart output = underTest.handle(itemId);

        // then
        assertThat(output).isNull();
    }
}
