package com.exo.ecommerce.infrastructure.http.presentation.getallitems;

import com.exo.ecommerce.domain.item.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemsPresenter {
    public ArrayList<ItemResponse> present(List<Item> items) {
        ArrayList<ItemResponse> response = new ArrayList<ItemResponse>();
        for (Item item : items) {
            response.add(new ItemResponse(item));
        }
        return response;
    }
}
