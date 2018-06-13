package com.exo.ecommerce.domain.item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Optional<Item> findById(long id);

    Item save(Item item);

    List<Item> findAll();
}
