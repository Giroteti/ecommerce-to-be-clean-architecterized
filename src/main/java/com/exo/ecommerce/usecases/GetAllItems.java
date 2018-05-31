package com.exo.ecommerce.usecases;

import com.exo.ecommerce.domain.Item;
import com.exo.ecommerce.domain.ItemRepository;

import java.util.ArrayList;
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
