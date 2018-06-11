package com.exo.ecommerce.domain.item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    public Optional<Item> findById(long id);

    public Item save(Item item);

    public List<Item> findAll();
}
