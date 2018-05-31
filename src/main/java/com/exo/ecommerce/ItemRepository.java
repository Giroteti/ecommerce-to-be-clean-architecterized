package com.exo.ecommerce;

import com.exo.ecommerce.domain.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {

}