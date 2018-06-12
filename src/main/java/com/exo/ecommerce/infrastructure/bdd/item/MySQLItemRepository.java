package com.exo.ecommerce.infrastructure.bdd.item;

import com.exo.ecommerce.domain.item.Item;
import com.exo.ecommerce.domain.item.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLItemRepository implements ItemRepository {

    ItemCRUDRepository crudRepository;

    public MySQLItemRepository(ItemCRUDRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Optional<Item> findById(long id) {
        return crudRepository.findById(id).map(com.exo.ecommerce.infrastructure.bdd.item.Item::toDomainEntity);
    }

    @Override
    public Item save(Item item) {
        return this.crudRepository.save(com.exo.ecommerce.infrastructure.bdd.item.Item.fromDomainEntity(item)).toDomainEntity();
    }

    @Override
    public List<Item> findAll() {
        ArrayList<Item> items = new ArrayList<Item>();
        for (com.exo.ecommerce.infrastructure.bdd.item.Item item : crudRepository.findAll()) {
            items.add(item.toDomainEntity());
        }
        return items;
    }
}
