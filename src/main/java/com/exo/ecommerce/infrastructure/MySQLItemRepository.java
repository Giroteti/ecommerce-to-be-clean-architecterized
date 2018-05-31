package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.domain.Item;

import java.util.Optional;

public class MySQLItemRepository implements com.exo.ecommerce.domain.ItemRepository {

    ItemCRUDRepository crudRepository;

    public MySQLItemRepository(ItemCRUDRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Optional<Item> findById(long id) {
        return crudRepository.findById(id);
    }

    @Override
    public Item save(Item item) {
        return this.crudRepository.save(item);
    }
}
