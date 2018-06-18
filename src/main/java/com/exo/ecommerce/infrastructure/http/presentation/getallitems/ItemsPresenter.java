package com.exo.ecommerce.infrastructure.http.presentation.getallitems;

import com.exo.ecommerce.domain.item.Item;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ItemsPresenter {
    public List<ItemResponse> present(List<Item> items) {
        return items.stream()
                .map(ItemResponse::new)
                .collect(toList());
    }
}
