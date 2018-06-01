package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.domain.Item;
import com.exo.ecommerce.domain.ItemRepository;

import java.util.Optional;

public class ItemMySQLRepository implements ItemRepository {

    ItemCRUDRepository crudRepository;

    public ItemMySQLRepository(ItemCRUDRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Optional<Item> findById(long id) {
        return this.crudRepository.findById(id);
    }

    @Override
    public Item save(Item item) {
        return this.crudRepository.save(item);
    }
}
