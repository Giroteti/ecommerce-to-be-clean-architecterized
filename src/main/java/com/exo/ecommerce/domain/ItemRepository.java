package com.exo.ecommerce.domain;

import java.util.Optional;

public interface ItemRepository {
    public Optional<Item> findById(long id);

    public Item save(Item item);
}
