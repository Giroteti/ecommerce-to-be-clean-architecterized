package com.exo.ecommerce.usecases;

import com.exo.ecommerce.FastTests;
import com.exo.ecommerce.domain.item.Item;
import com.exo.ecommerce.domain.item.ItemRepository;
import com.exo.ecommerce.usecases.getallitems.GetAllItems;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GetAllItemsTests {
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private GetAllItems underTest;

    @Test
    @Category(FastTests.class)
    public void should_return_all_invoices() {
        // given
        List<Item> returnedList = new ArrayList<>();
        given(itemRepository.findAll()).willReturn(returnedList);

        // when
        List<Item> output = underTest.handle();

        // then
        assertThat(output).isEqualTo(returnedList);
    }

}
