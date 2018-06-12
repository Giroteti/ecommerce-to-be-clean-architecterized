package com.exo.ecommerce.usecases.getallitems;

import com.exo.ecommerce.domain.item.Item;
import com.exo.ecommerce.domain.item.ItemRepository;

import java.util.List;

public class GetAllItems {
    private ItemRepository itemRepository;

    public GetAllItems(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> handle() {
        return itemRepository.findAll();
    }
}
