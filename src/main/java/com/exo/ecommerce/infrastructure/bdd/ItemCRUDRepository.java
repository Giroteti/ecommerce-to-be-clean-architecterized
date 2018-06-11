package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.domain.item.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemCRUDRepository extends CrudRepository<Item, Long> {

}