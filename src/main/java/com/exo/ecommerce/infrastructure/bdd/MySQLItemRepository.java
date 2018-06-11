package com.exo.ecommerce.infrastructure.bdd;

import com.exo.ecommerce.domain.item.Item;
import com.exo.ecommerce.domain.item.ItemRepository;

import java.util.List;
import java.util.Optional;

public class MySQLItemRepository implements ItemRepository {

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

    @Override
    public List<Item> findAll() {
        return (List<Item>) crudRepository.findAll();
    }
}
